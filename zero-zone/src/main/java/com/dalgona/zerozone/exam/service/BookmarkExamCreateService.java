package com.dalgona.zerozone.exam.service;

import com.dalgona.zerozone.bookmark.domain.BookmarkedReadingProb;
import com.dalgona.zerozone.bookmark.repository.BookmarkedReadingProbRepository;
import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.exam.domain.Exam;
import com.dalgona.zerozone.exam.dto.ExamCreateRequestDto;
import com.dalgona.zerozone.exam.dto.ExamCreateResponseDto;
import com.dalgona.zerozone.exam.dto.ExamSettingInfoResponseDto;
import com.dalgona.zerozone.exam.repository.ExamRepository;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.dto.ReadingProbResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookmarkExamCreateService implements ExamCreateInterface {
    private final ExamRepository examRepository;
    private final BookmarkedReadingProbRepository bookmarkedReadingProbRepository;
    @Setter private Member member;
    private int requestProbCount;
    private int availableProbCount;
    private List<BookmarkedReadingProb> allBookmarkedReadingProbList;
    private List<ReadingProbResponseDto> examCreateResultList;

    @Transactional
    @Override
    public ExamSettingInfoResponseDto getExamSettingInfo() {
        int totalExamCount = examRepository.countByMember(member);
        int probCount = Math.toIntExact(bookmarkedReadingProbRepository.countByMember(member));
        return new ExamSettingInfoResponseDto(totalExamCount, probCount);
    }

    @Transactional
    @Override
    public ExamCreateResponseDto createExam(ExamCreateRequestDto examCreateRequestDto) {
        setRequestProbCount(examCreateRequestDto.getProbCount());
        setAvailableProbInfo();

        checkRequestProbCountValid();
        setExamCreateResultList();

        Exam newExam = examRepository.save(examCreateRequestDto.toEntity(member));
        return new ExamCreateResponseDto(newExam.getId(), newExam.getExamName(), examCreateResultList);
    }

    private void setRequestProbCount(int requestProbCount) {
        this.requestProbCount = requestProbCount;
    }

    private void setAvailableProbInfo() {
        List<BookmarkedReadingProb> bookmarkedReadingProbList = bookmarkedReadingProbRepository.findAllByMember(member);
        availableProbCount = bookmarkedReadingProbList.size();
        allBookmarkedReadingProbList = bookmarkedReadingProbList;
    }

    private void checkRequestProbCountValid() {
        if(requestProbCount < 1)
            throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "요청 문제 개수는 1개 이상이어야 합니다.");
        if(availableProbCount < requestProbCount)
            throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "요청한 문제 개수가 연습에 등록된 문제 개수보다 많습니다.");
    }

    private void setExamCreateResultList() {
        List<BookmarkedReadingProb> randomBookmarkedReadingProbList = extractBookmarkedReadingProbListRandomly();
        examCreateResultList = randomBookmarkedReadingProbList.stream().map(ReadingProbResponseDto::of).collect(Collectors.toList());
    }

    private List<BookmarkedReadingProb> extractBookmarkedReadingProbListRandomly() {
        Collections.shuffle(allBookmarkedReadingProbList);
        List<BookmarkedReadingProb> examReadingProbList = new ArrayList<>();
        for(int i = 0; i < requestProbCount; i++){
            examReadingProbList.add(allBookmarkedReadingProbList.get(i));
        }
        return examReadingProbList;
    }

}
