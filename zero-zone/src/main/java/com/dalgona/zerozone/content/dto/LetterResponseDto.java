package com.dalgona.zerozone.content.dto;

import com.dalgona.zerozone.content.domain.Coda;
import com.dalgona.zerozone.content.domain.Letter;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
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

    Long probId;

    @Builder
    public LetterResponseDto(SpeakingProb speakingProb){
        Coda coda = speakingProb.getLetter().getCoda();
        Letter letter = speakingProb.getLetter();
        if(coda != null){
            this.codaId = coda.getId();
            this.coda = coda.getCoda();
        }
        this.letterId = letter.getId();
        this.letter = letter.getLetter();
        this.probId = speakingProb.getId();
    }

    @Override
    public int compareTo(LetterResponseDto dto) {
        Long l = this.codaId - dto.getCodaId();
        return l.intValue();
    }
}

