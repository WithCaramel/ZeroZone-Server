package com.dalgona.zerozone.exam.service;

import com.dalgona.zerozone.bookmark.repository.BookmarkedReadingProbRepository;
import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.exam.domain.Exam;
import com.dalgona.zerozone.exam.dto.ExamCreateRequestDto;
import com.dalgona.zerozone.exam.dto.ExamCreateResponseDto;
import com.dalgona.zerozone.exam.dto.ExamSettingInfoResponseDto;
import com.dalgona.zerozone.exam.dto.ReadingProbAndBookmarkJoin;
import com.dalgona.zerozone.exam.repository.ExamRepository;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.ContentType;
import com.dalgona.zerozone.practice.dto.ReadingProbResponseDto;
import com.dalgona.zerozone.practice.repository.ReadingProbRepository;
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
public class SentenceExamCreateService implements ExamCreateInterface {
    private final ExamRepository examRepository;
    private final ReadingProbRepository readingProbRepository;
    @Setter private Member member;
    private int requestProbCount;
    private int availableProbCount;
    private List<ReadingProbAndBookmarkJoin> allReadingProbList;
    private List<ReadingProbResponseDto> readingProbResponseDtoList;

    @Transactional
    @Override
    public ExamSettingInfoResponseDto getExamSettingInfo() {
        int totalExamCount = examRepository.countByMember(member);
        int sentenceCount = readingProbRepository.countByType(ContentType.SENTENCE);
        return new ExamSettingInfoResponseDto(totalExamCount, sentenceCount);
    }

    @Transactional
    @Override
    public ExamCreateResponseDto createExam(ExamCreateRequestDto examCreateRequestDto) {
        setRequestProbCount(examCreateRequestDto);
        setAvailableProbInfo();

        checkRequestProbCountValid();
        setExamCreateResultList();

        Exam newExam = examRepository.save(examCreateRequestDto.toEntity(member));
        return new ExamCreateResponseDto(newExam.getId(), newExam.getExamName(), readingProbResponseDtoList);
    }

    private void setRequestProbCount(ExamCreateRequestDto examCreateRequestDto) {
        requestProbCount = examCreateRequestDto.getProbCount();
    }

    private void setAvailableProbInfo() {
        List<ReadingProbAndBookmarkJoin> readingProbList = readingProbRepository.readingProbLeftJoinBookmarkByType(ContentType.SENTENCE);
        availableProbCount = readingProbList.size();
        allReadingProbList = readingProbList;
    }

    private void setExamCreateResultList() {
        List<ReadingProbAndBookmarkJoin> randomJoinList = extractSentenceReadingProbListRandomly();
        readingProbResponseDtoList = randomJoinList.stream().map(ReadingProbResponseDto::of).collect(Collectors.toList());
    }

    private List<ReadingProbAndBookmarkJoin> extractSentenceReadingProbListRandomly() {
        Collections.shuffle(allReadingProbList);
        List<ReadingProbAndBookmarkJoin> sentenceExamReadingProbList = new ArrayList<>();
        for(int i = 0; i < requestProbCount; i++){
            sentenceExamReadingProbList.add(allReadingProbList.get(i));
        }
        return sentenceExamReadingProbList;
    }

    private void checkRequestProbCountValid() {
        if(requestProbCount < 1)
            throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "요청 문제 개수는 1개 이상이어야 합니다.");
        if(availableProbCount < requestProbCount)
            throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "요청한 문제 개수가 연습에 등록된 문제 개수보다 많습니다.");
    }
}
