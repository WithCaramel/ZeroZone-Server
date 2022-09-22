package com.dalgona.zerozone.exam.controller;

import com.dalgona.zerozone.exam.dto.ExamCreateRequestDto;
import com.dalgona.zerozone.exam.dto.ExamCreateResponseDto;
import com.dalgona.zerozone.exam.dto.ExamSettingInfoResponseDto;
import com.dalgona.zerozone.exam.service.BookmarkExamCreateService;
import com.dalgona.zerozone.exam.service.IntegrateExamCreateService;
import com.dalgona.zerozone.exam.service.SentenceExamCreateService;
import com.dalgona.zerozone.exam.service.WordExamCreateService;
import com.dalgona.zerozone.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/reading-practices/exams")
public class ExamCreateController {
    public final WordExamCreateService wordExamCreateService;
    private final SentenceExamCreateService sentenceExamCreateService;
    private final IntegrateExamCreateService integrateExamCreateService;
    private final BookmarkExamCreateService bookmarkExamCreateService;

    @GetMapping("/word")
    public ExamSettingInfoResponseDto getWordExamSettingInfo(@AuthenticationPrincipal Member member){
        wordExamCreateService.setMember(member);
        return wordExamCreateService.getExamSettingInfo();
    }

    @PostMapping("/word")
    public ExamCreateResponseDto createWordExam(
            @RequestBody @Valid ExamCreateRequestDto examCreateRequestDto,
            @AuthenticationPrincipal Member member
    ){
        wordExamCreateService.setMember(member);
        return wordExamCreateService.createExam(examCreateRequestDto);
    }

    @GetMapping("/sentence")
    public ExamSettingInfoResponseDto getSentenceExamSettingInfo(@AuthenticationPrincipal Member member){
        sentenceExamCreateService.setMember(member);
        return sentenceExamCreateService.getExamSettingInfo();
    }

    @PostMapping("/sentence")
    public ExamCreateResponseDto createSentenceExam(
            @RequestBody @Valid ExamCreateRequestDto examCreateRequestDto,
            @AuthenticationPrincipal Member member
    ){
        sentenceExamCreateService.setMember(member);
        return sentenceExamCreateService.createExam(examCreateRequestDto);
    }

    @GetMapping("/integrate")
    public ExamSettingInfoResponseDto getIntegrateExamSettingInfo(@AuthenticationPrincipal Member member){
        integrateExamCreateService.setMember(member);
        return integrateExamCreateService.getExamSettingInfo();
    }

    @PostMapping("/integrate")
    public ExamCreateResponseDto createIntegrateExam(
            @RequestBody @Valid ExamCreateRequestDto examCreateRequestDto,
            @AuthenticationPrincipal Member member
    ){
        integrateExamCreateService.setMember(member);
        return integrateExamCreateService.createExam(examCreateRequestDto);
    }

    @GetMapping("/bookmark")
    public ExamSettingInfoResponseDto getBookmarkExamSettingInfo(@AuthenticationPrincipal Member member){
        bookmarkExamCreateService.setMember(member);
        return bookmarkExamCreateService.getExamSettingInfo();
    }

    @PostMapping("/bookmark")
    public ExamCreateResponseDto createBookmarkExam(
            @RequestBody @Valid ExamCreateRequestDto examCreateRequestDto,
            @AuthenticationPrincipal Member member
    ){
        bookmarkExamCreateService.setMember(member);
        return bookmarkExamCreateService.createExam(examCreateRequestDto);
    }

}
