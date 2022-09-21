package com.dalgona.zerozone.practice.dto;

import com.dalgona.zerozone.practice.domain.ContentType;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpeakingProbResponseDto {
    Long probId;
    ContentType type;
    Long contentId;
    String content;
    String url;
    boolean isBookmarked;

    public static SpeakingProbResponseDto of(SpeakingProb practice, boolean isBookmarked) {
        ContentType typeOfRequestSpeakingProb = practice.getType();
        SpeakingProbResponseDto result = new SpeakingProbResponseDto();

        if(typeOfRequestSpeakingProb == ContentType.LETTER){
            result = SpeakingProbResponseDto.builder()
                    .probId(practice.getId())
                    .type(typeOfRequestSpeakingProb)
                    .contentId(practice.getLetter().getId())
                    .content(practice.getLetter().getLetter())
                    .url(practice.getUrl())
                    .isBookmarked(isBookmarked)
                    .build();
        }
        else if(typeOfRequestSpeakingProb == ContentType.WORD){
            result = SpeakingProbResponseDto.builder()
                    .probId(practice.getId())
                    .type(typeOfRequestSpeakingProb)
                    .contentId(practice.getWord().getId())
                    .content(practice.getWord().getWord())
                    .url(practice.getUrl())
                    .isBookmarked(isBookmarked)
                    .build();
        }
        else {
            result = SpeakingProbResponseDto.builder()
                    .probId(practice.getId())
                    .type(typeOfRequestSpeakingProb)
                    .contentId(practice.getSentence().getId())
                    .content(practice.getSentence().getSentence())
                    .url(practice.getUrl())
                    .isBookmarked(isBookmarked)
                    .build();
        }
        return result;
    }
}
