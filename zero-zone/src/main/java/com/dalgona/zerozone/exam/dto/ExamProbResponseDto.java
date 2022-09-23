package com.dalgona.zerozone.exam.dto;

import com.dalgona.zerozone.exam.domain.ExamProb;
import com.dalgona.zerozone.practice.domain.ContentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExamProbResponseDto {

    Long examProbId;
    ContentType type;
    String content;
    boolean isCorrect;

    public ExamProbResponseDto(ExamProb examProb){
        this.examProbId = examProb.getId();
        this.type = examProb.getReadingProb().getType();
        if(type == ContentType.WORD) this.content = examProb.getReadingProb().getWord().getWord();
        else this.content = examProb.getReadingProb().getSentence().getSentence();
        this.isCorrect = examProb.isCorrect();
    }
}
