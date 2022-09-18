package com.dalgona.zerozone.content.dto;

import com.dalgona.zerozone.content.domain.Letter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LetterResponseDto implements Comparable<LetterResponseDto> {

    Long codaId;
    String coda;
    Long letterId;
    String letter;

    @Builder
    public LetterResponseDto(Letter letter){
        if(letter.getCoda() != null){
            this.codaId = letter.getCoda().getId();
            this.coda = letter.getCoda().getCoda();
        }
        this.letterId = letter.getId();
        this.letter = letter.getLetter();
    }

    @Override
    public int compareTo(LetterResponseDto dto) {
        Long l = this.codaId - dto.getCodaId();
        return l.intValue();
    }
}

