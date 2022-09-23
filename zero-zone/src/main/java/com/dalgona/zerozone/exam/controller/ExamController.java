package com.dalgona.zerozone.exam.controller;

import com.dalgona.zerozone.common.response.OnlyResponseString;
import com.dalgona.zerozone.exam.dto.*;
import com.dalgona.zerozone.exam.service.CommonExamService;
import com.dalgona.zerozone.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/reading-practices/exams")
public class ExamController {
    private final CommonExamService commonExamService;

    @GetMapping
    public Page<ExamInfoResponseDto> getExamListOfMember(
            @AuthenticationPrincipal Member member,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page
    ){
        commonExamService.setMember(member);
        return commonExamService.getExamListOfMember(page);
    }

    @GetMapping("/{examId}")
    public Page<ExamProbResponseDto> getExamProbListOfExam(
            @AuthenticationPrincipal Member member,
            @PathVariable Long examId,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page
    ){
        commonExamService.setMember(member);
        return commonExamService.getExamProbListOfExam(examId, page);
    }

    @GetMapping("/probs/{examProbId}/result")
    public ExamProbResultResponseDto getExamProbResult(
            @AuthenticationPrincipal Member member,
            @PathVariable Long examProbId
    ){
        commonExamService.setMember(member);
        return commonExamService.getExamProbResult(examProbId);
    }

    @PostMapping("/{examId}/result")
    public Long updateExamProbsResult(
            @AuthenticationPrincipal Member member,
            @PathVariable Long examId,
            @RequestBody @Valid ExamResultUpdateRequestDto requestDto
            ){
        commonExamService.setMember(member);
        return commonExamService.updateExamProbsResult(examId, requestDto);
    }

    @PatchMapping("/{examId}/name")
    public OnlyResponseString updateExamName(
            @AuthenticationPrincipal Member member,
            @PathVariable Long examId,
            @RequestBody @Valid ExamNameUpdateRequestDto requestDto
    ){
        commonExamService.setMember(member);
        commonExamService.updateExamName(examId, requestDto.getExamName());
        return OnlyResponseString.of("시험 이름 수정에 성공했습니다.");
    }
    
    @DeleteMapping("/{examId}")
    public OnlyResponseString deleteExam(
            @AuthenticationPrincipal Member member,
            @PathVariable Long examId
    ){
        commonExamService.setMember(member);
        commonExamService.deleteExam(examId);
        return OnlyResponseString.of("시험 삭제에 성공했습니다.");
    }

}
