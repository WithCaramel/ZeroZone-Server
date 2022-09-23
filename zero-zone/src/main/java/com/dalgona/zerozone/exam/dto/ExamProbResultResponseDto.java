package com.dalgona.zerozone.exam.dto;

import com.dalgona.zerozone.exam.domain.ExamProb;
import com.dalgona.zerozone.practice.dto.ReadingProbResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExamProbResultResponseDto {
    private Long probId;
    private int index;
    private boolean usedHint;
    private boolean isCorrect;
    private ReadingProbResponseDto readingProb;
    private Long nextProbId;

    public ExamProbResultResponseDto(ExamProb examProb, Long nextProbId, boolean isBookmarked){
        this.probId = examProb.getId();
        this.index = examProb.getIndex();
        this.usedHint = examProb.isUsedHint();
        this.isCorrect = examProb.isCorrect();
        this.readingProb = ReadingProbResponseDto.of(examProb.getReadingProb(), isBookmarked);
        this.nextProbId = nextProbId;
    }
}
