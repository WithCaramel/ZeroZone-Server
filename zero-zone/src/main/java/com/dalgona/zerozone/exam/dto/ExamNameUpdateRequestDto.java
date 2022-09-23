package com.dalgona.zerozone.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExamNameUpdateRequestDto {
    @NotNull(message = "시험 이름은 null일 수 없습니다.")
    @NotEmpty(message = "시험 이름은 빈 값일 수 없습니다.")
    String examName;
}
