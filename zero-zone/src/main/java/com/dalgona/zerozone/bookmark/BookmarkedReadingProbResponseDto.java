package com.dalgona.zerozone.bookmark;

import com.dalgona.zerozone.practice.domain.ContentType;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.dto.ReadingProbResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkedReadingProbResponseDto {
    ContentType type;
    Long contentId;
    String content;

    public static BookmarkedReadingProbResponseDto of(BookmarkedReadingProb bookmarkedReadingProb) {
        ReadingProb practice = bookmarkedReadingProb.getReadingProb();
        ContentType typeOfRequestReadingProb = practice.getType();
        BookmarkedReadingProbResponseDto result = new BookmarkedReadingProbResponseDto();

        if(typeOfRequestReadingProb == ContentType.WORD){
            result = BookmarkedReadingProbResponseDto.builder()
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getWord().getId())
                    .content(practice.getWord().getWord())
                    .build();
        }
        else{
            result = BookmarkedReadingProbResponseDto.builder()
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getSentence().getId())
                    .content(practice.getSentence().getSentence())
                    .build();
        }
        return result;
    }
}
