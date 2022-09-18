package com.dalgona.zerozone.content.dto;

import com.dalgona.zerozone.content.domain.Sentence;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SentenceResponseDto implements Comparable<SentenceResponseDto> {

    Long sentenceId;
    String sentence;

    @Builder
    public SentenceResponseDto(Sentence sentence){
        this.sentenceId = sentence.getId();
        this.sentence = sentence.getSentence();
    }


    @Override
    public int compareTo(SentenceResponseDto s) {
        Long l = this.getSentenceId() - s.getSentenceId();
        return l.intValue();
    }
}
