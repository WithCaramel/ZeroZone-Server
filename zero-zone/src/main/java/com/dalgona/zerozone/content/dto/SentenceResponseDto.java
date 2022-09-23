package com.dalgona.zerozone.content.dto;

import com.dalgona.zerozone.content.domain.Sentence;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SentenceResponseDto implements Comparable<SentenceResponseDto> {

    Long sentenceId;
    String sentence;
    Long probId;

    @Builder
    public SentenceResponseDto(SpeakingProb speakingProb){
        this.sentenceId = speakingProb.getSentence().getId();
        this.sentence = speakingProb.getSentence().getSentence();
        this.probId = speakingProb.getId();
    }

    @Builder
    public SentenceResponseDto(ReadingProb readingProb){
        this.sentenceId = readingProb.getSentence().getId();
        this.sentence = readingProb.getSentence().getSentence();
        this.probId = readingProb.getId();
    }


    @Override
    public int compareTo(SentenceResponseDto s) {
        Long l = this.getSentenceId() - s.getSentenceId();
        return l.intValue();
    }
}
