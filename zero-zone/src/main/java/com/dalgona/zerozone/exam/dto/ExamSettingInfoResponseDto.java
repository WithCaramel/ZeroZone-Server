package com.dalgona.zerozone.exam.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExamSettingInfoResponseDto {
    int totalExamCount;
    int totalProbCount;

    @Builder
    public ExamSettingInfoResponseDto(int totalExamCount, int totalProbCount){
        this.totalExamCount = totalExamCount;
        this.totalProbCount = totalProbCount;
    }
}
