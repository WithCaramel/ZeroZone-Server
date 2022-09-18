package com.dalgona.zerozone.content.dto;

import com.dalgona.zerozone.content.domain.Word;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WordResponseDto implements Comparable<WordResponseDto> {

    Long wordId;
    String word;

    @Builder
    public WordResponseDto(Word word){
        this.wordId = word.getId();
        this.word = word.getWord();
    }

    @Override
    public int compareTo(WordResponseDto w) {
        Long l = this.getWordId() - w.getWordId();
        return l.intValue();
    }

}

