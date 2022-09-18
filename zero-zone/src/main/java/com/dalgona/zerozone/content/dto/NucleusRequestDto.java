package com.dalgona.zerozone.content.dto;

import com.dalgona.zerozone.content.domain.Onset;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NucleusRequestDto {

    Long onsetId;
    String onset;

    @Builder
    public NucleusRequestDto(Long onsetId, String onset){
        this.onsetId=onsetId;
        this.onset=onset;
    }

    public Onset toEntity(){
        return Onset.builder()
                .id(onsetId)
                .onset(onset)
                .build();
    }

}

