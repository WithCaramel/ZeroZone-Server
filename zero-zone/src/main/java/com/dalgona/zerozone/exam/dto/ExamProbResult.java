package com.dalgona.zerozone.exam.dto;

import com.dalgona.zerozone.exam.domain.Exam;
import com.dalgona.zerozone.exam.domain.ExamProb;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExamProbResult {
    Long readingProbId;
    int index;
    boolean usedHint;
    boolean correct;

    public ExamProb toEntity(ExamProbResult examProbResult, ReadingProb readingProb, Exam exam) {
        return ExamProb.builder()
                .index(examProbResult.getIndex())
                .usedHint(examProbResult.isUsedHint())
                .isCorrect(examProbResult.isCorrect())
                .readingProb(readingProb)
                .exam(exam)
                .build();
    }
}
