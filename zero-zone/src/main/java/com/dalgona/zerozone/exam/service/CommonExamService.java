package com.dalgona.zerozone.exam.service;

import com.dalgona.zerozone.bookmark.repository.BookmarkedReadingProbRepository;
import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.exam.domain.Exam;
import com.dalgona.zerozone.exam.domain.ExamProb;
import com.dalgona.zerozone.exam.dto.*;
import com.dalgona.zerozone.exam.repository.ExamProbRepository;
import com.dalgona.zerozone.exam.repository.ExamRepository;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.repository.ReadingProbRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonExamService {

    private final ExamRepository examRepository;
    private final ExamProbRepository examProbRepository;
    private final BookmarkedReadingProbRepository bookmarkedReadingProbRepository;
    private final ReadingProbRepository readingProbRepository;
    @Setter private Member member;
    final int PAGE_SIZE = 10;

    @Transactional
    public Page<ExamInfoResponseDto> getExamListOfMember(int page){
        int validPage = getValidPage(page, true);
        PageRequest paging = PageRequest.of(validPage - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Exam> examPage = examRepository.findAllByMember(member, paging);
        return examPage.map(ExamInfoResponseDto::new);
    }

    private int getValidPage(int requestPage, boolean isExam) {
        checkRequestPageValid(requestPage);
        int totalPage;
        if(isExam) totalPage = calculateTotalPageOfExamList();
        else totalPage = calculateTotalPageOfExamProbList();
        return (totalPage < requestPage) ? totalPage : requestPage;
    }

    private void checkRequestPageValid(int requestPage) {
        if(requestPage < 1) throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "page는 1보다 작을 수 없습니다.");
    }

    private int calculateTotalPageOfExamList() {
        int examCount = examRepository.countByMember(member);
        int totalPage = (int) Math.ceil((examCount + 0.0) / PAGE_SIZE);
        return (totalPage < 1) ? 1 : totalPage;
    }

    @Transactional
    public Page<ExamProbResponseDto> getExamProbListOfExam(Long examId, int page){
        Exam exam = getExamOrElseThrow(examId);
        int validPage = getValidPage(page, false);
        PageRequest paging = PageRequest.of(validPage - 1, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "id"));
        Page<ExamProb> examProbPage = examProbRepository.findAllByExam(exam, paging);
        return examProbPage.map(ExamProbResponseDto::new);
    }

    private Exam getExamOrElseThrow(Long examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "해당 시험은 존재하지 않습니다."));
    }

    private int calculateTotalPageOfExamProbList() {
        int examProbCount = examRepository.countByMember(member);
        int totalPage = (int) Math.ceil((examProbCount + 0.0) / PAGE_SIZE);
        return (totalPage < 1) ? 1 : totalPage;
    }

    // TODO : 개선 - 검색 조건이 두개, bookmark도 같이 조인
    @Transactional
    public ExamProbResultResponseDto getExamProbResult(Long examProbId){
        ExamProb examProb = getExamProbOrElseThrow(examProbId);
        Long nextProbId = getNextProbId(examProbId, examProb);
        boolean isBookmarked = bookmarkedReadingProbRepository.existsByReadingProbAndMember(examProb.getReadingProb(), member);
        return new ExamProbResultResponseDto(examProb, nextProbId, isBookmarked);
    }

    private Long getNextProbId(Long examProbId, ExamProb examProb) {
        Exam exam = getExamOrElseThrow(examProb.getExam().getId());
        int currentIndex = examProb.getIndex();
        Long nextProbId = (currentIndex >= exam.getProbCount()) ? null : getExamProbOrElseThrow(examProbId +1).getId();
        return nextProbId;
    }

    private ExamProb getExamProbOrElseThrow(Long examProbId) {
        return examProbRepository.findById(examProbId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 시험 문제입니다."));
    }


    @Transactional
    public Long updateExamProbsResult(Long examProbId, ExamResultUpdateRequestDto resultUpdateRequestDto){
        Exam exam = getExamOrElseThrow(examProbId);

        List<ExamProbResult> examResultList = resultUpdateRequestDto.getExamProbResultList();

        if(exam.getExamProbList().size() != 0)
            throw new BadRequestException(BadRequestErrorCode.DUPLICATED, "이미 채점을 완료한 시험입니다.");


        if(examResultList.size() != exam.getProbCount())
            throw new BadRequestException(BadRequestErrorCode.NOT_MATCHES, "시험의 문제 개수와 채점 요청한 문제 개수가 일치하지 않습니다.");

        if(resultUpdateRequestDto.getCorrectCount() < 0 || resultUpdateRequestDto.getCorrectCount() > exam.getProbCount())
            throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "맞은 개수의 범위가 유효하지 않습니다.");

        ArrayList<Integer> indexList = getIndexList(examResultList);
        checkIndexInfoValid(indexList);

        for(ExamProbResult examProbResult:examResultList){
            Long readingProbId = examProbResult.getReadingProbId();
            ReadingProb readingProb = readingProbRepository.findById(readingProbId)
                    .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "일련 번호가 "+readingProbId+"인 구화 문제를 찾을 수 없습니다."));
            examProbRepository.save(examProbResult.toEntity(examProbResult, readingProb, exam));
        }
        exam.updateCorrectCount(resultUpdateRequestDto.getCorrectCount());
        return exam.getId();
    }

    private static ArrayList<Integer> getIndexList(List<ExamProbResult> examResultList) {
        ArrayList<Integer> indexList = new ArrayList<>();
        for(ExamProbResult examProbResult: examResultList){
            indexList.add(examProbResult.getIndex());
        }
        return indexList;
    }

    private void checkIndexInfoValid(ArrayList<Integer> indexInfo) {
        if (indexInfo.size() <= 1) {
            return;
        }

        Integer min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i: indexInfo){
            if (i < min) { min = i; }
            if (i > max) { max = i; }
        }

        if(min != 1) throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "요청한 인덱스가 1로 시작하지 않습니다.");

        Set<Integer> visited = new HashSet<>();
        for (int i: indexInfo) {
            if (visited.contains(i)) {
                throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "요청한 인덱스에 중복이 있습니다.");
            }
            visited.add(i);
        }

        if (max - min != indexInfo.size() - 1) {
            throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "요청한 인덱스의 끝값을 다시 확인해주세요.");
        }

    }


    @Transactional
    public void updateExamName(Long examId, String newName){
        Exam exam = getExamOrElseThrow(examId);
        exam.updateExamName(newName);
    }

    @Transactional
    public void deleteExam(Long examId){
        if(examRepository.existsById(examId)) examRepository.deleteById(examId);
        else throw new BadRequestException(BadRequestErrorCode.NOT_FOUND, "해당 시험은 존재하지 않습니다.");
    }


}
