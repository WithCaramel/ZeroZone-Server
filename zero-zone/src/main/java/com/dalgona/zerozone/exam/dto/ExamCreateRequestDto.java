package com.dalgona.zerozone.exam.dto;

import com.dalgona.zerozone.exam.domain.Exam;
import com.dalgona.zerozone.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ExamCreateRequestDto {

    @NotNull(message = "시험 이름은 null일 수 없습니다.")
    @NotEmpty(message = "시험 이름은 빈 값일 수 없습니다.")
    String examName;

    @NotNull(message = "문제 개수는 null일 수 없습니다.")
    int probCount;

    public Exam toEntity(Member member){
        return Exam.builder()
                .examName(examName)
                .probCount(probCount)
                .correctCount(0)
                .member(member)
                .build();
    }
}
