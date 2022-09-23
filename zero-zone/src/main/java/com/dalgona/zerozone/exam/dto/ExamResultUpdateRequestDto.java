package com.dalgona.zerozone.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultUpdateRequestDto {
    @NotNull(message = "문제 채점 결과는 null일 수 없습니다.")
    List<ExamProbResult> examProbResultList;

    @NotNull(message = "맞은 문제 개수는 null일 수 없습니다.")
    @Min(value = 0, message = "맞은 문제 개수는 0개 이상이어야 합니다.")
    int correctCount;
}
