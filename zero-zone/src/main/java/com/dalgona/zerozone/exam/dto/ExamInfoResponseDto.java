package com.dalgona.zerozone.exam.dto;

import com.dalgona.zerozone.exam.domain.Exam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExamInfoResponseDto {
    Long examId;
    String examName;
    int correctCount;
    int probCount;
    LocalDateTime date;

    public ExamInfoResponseDto(Exam exam){
        this.examId = exam.getId();
        this.examName = exam.getExamName();
        this.correctCount = exam.getCorrectCount();
        this.probCount = exam.getProbCount();
        this.date = exam.getModifiedDate();
    }

}
