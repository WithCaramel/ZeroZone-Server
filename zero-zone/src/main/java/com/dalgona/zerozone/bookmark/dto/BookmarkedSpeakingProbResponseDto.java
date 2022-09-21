package com.dalgona.zerozone.bookmark.dto;

import com.dalgona.zerozone.bookmark.domain.BookmarkedReadingProb;
import com.dalgona.zerozone.bookmark.domain.BookmarkedSpeakingProb;
import com.dalgona.zerozone.practice.domain.ContentType;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkedSpeakingProbResponseDto {

    ContentType type;
    Long contentId;
    String content;

    public static BookmarkedSpeakingProbResponseDto of(BookmarkedSpeakingProb bookmarkedSpeakingProb) {
        SpeakingProb practice = bookmarkedSpeakingProb.getSpeakingProb();
        ContentType typeOfRequestReadingProb = practice.getType();
        BookmarkedSpeakingProbResponseDto result = new BookmarkedSpeakingProbResponseDto();

        if(typeOfRequestReadingProb == ContentType.LETTER){
            result = BookmarkedSpeakingProbResponseDto.builder()
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getLetter().getId())
                    .content(practice.getLetter().getLetter())
                    .build();
        }
        else if(typeOfRequestReadingProb == ContentType.WORD){
            result = BookmarkedSpeakingProbResponseDto.builder()
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getWord().getId())
                    .content(practice.getWord().getWord())
                    .build();
        }
        else {
            result = BookmarkedSpeakingProbResponseDto.builder()
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getSentence().getId())
                    .content(practice.getSentence().getSentence())
                    .build();
        }
        return result;
    }

}
