package com.dalgona.zerozone.content.dto;

import com.dalgona.zerozone.content.domain.Word;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WordResponseDto implements Comparable<WordResponseDto> {

    Long wordId;
    String word;
    Long probId;

    @Builder
    public WordResponseDto(ReadingProb readingProb){
        this.wordId = readingProb.getWord().getId();
        this.word = readingProb.getWord().getWord();
        this.probId = readingProb.getId();
    }

    @Builder
    public WordResponseDto(SpeakingProb speakingProb){
        this.wordId = speakingProb.getWord().getId();
        this.word = speakingProb.getWord().getWord();
        this.probId = speakingProb.getId();
    }

    @Override
    public int compareTo(WordResponseDto w) {
        Long l = this.getWordId() - w.getWordId();
        return l.intValue();
    }

}

