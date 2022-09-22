package com.dalgona.zerozone.practice.dto;

import com.dalgona.zerozone.bookmark.domain.BookmarkedReadingProb;
import com.dalgona.zerozone.exam.dto.ReadingProbAndBookmarkJoin;
import com.dalgona.zerozone.practice.domain.ContentType;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadingProbResponseDto {

    Long probId;
    ContentType type;
    Long contentId;
    String content;
    String url;
    String hint;
    String spacingInfo;
    boolean isBookmarked;

    public static ReadingProbResponseDto of(ReadingProbAndBookmarkJoin probAndBookmarkJoin) {
        Long bookmarkReadingProbId = probAndBookmarkJoin.getBookmarkReadingProbId();
        boolean isBookmarked = (bookmarkReadingProbId == null)? false : true;

        ContentType typeOfRequestReadingProb = probAndBookmarkJoin.getType();
        ReadingProbResponseDto result = new ReadingProbResponseDto();

        if(typeOfRequestReadingProb == ContentType.WORD){
            result = ReadingProbResponseDto.builder()
                    .probId(probAndBookmarkJoin.getReadingProbId())
                    .type(typeOfRequestReadingProb)
                    .contentId(probAndBookmarkJoin.getWordId())
                    .content(probAndBookmarkJoin.getWord())
                    .url(probAndBookmarkJoin.getUrl())
                    .hint(probAndBookmarkJoin.createHint())
                    .isBookmarked(isBookmarked)
                    .build();
        }
        else{
            result = ReadingProbResponseDto.builder()
                    .probId(probAndBookmarkJoin.getReadingProbId())
                    .type(typeOfRequestReadingProb)
                    .contentId(probAndBookmarkJoin.getSentenceId())
                    .content(probAndBookmarkJoin.getSentence())
                    .url(probAndBookmarkJoin.getUrl())
                    .hint(probAndBookmarkJoin.createHint())
                    .spacingInfo(probAndBookmarkJoin.createSpacingInfo())
                    .isBookmarked(isBookmarked)
                    .build();
        }
        return result;
    }

    public static ReadingProbResponseDto of(ReadingProb practice, boolean isBookmarked) {
        ContentType typeOfRequestReadingProb = practice.getType();
        ReadingProbResponseDto result = new ReadingProbResponseDto();

        if(typeOfRequestReadingProb == ContentType.WORD){
            result = ReadingProbResponseDto.builder()
                    .probId(practice.getId())
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getWord().getId())
                    .content(practice.getWord().getWord())
                    .url(practice.getUrl())
                    .hint(practice.createHint())
                    .isBookmarked(isBookmarked)
                    .build();
        }
        else{
            result = ReadingProbResponseDto.builder()
                    .probId(practice.getId())
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getSentence().getId())
                    .content(practice.getSentence().getSentence())
                    .url(practice.getUrl())
                    .hint(practice.createHint())
                    .spacingInfo(practice.createSpacingInfo())
                    .isBookmarked(isBookmarked)
                    .build();
        }
        return result;
    }

    public static ReadingProbResponseDto of(BookmarkedReadingProb bookmarkedReadingProb) {
        ReadingProb practice = bookmarkedReadingProb.getReadingProb();
        ContentType typeOfRequestReadingProb = practice.getType();
        ReadingProbResponseDto result = new ReadingProbResponseDto();

        if(typeOfRequestReadingProb == ContentType.WORD){
            result = ReadingProbResponseDto.builder()
                    .probId(practice.getId())
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getWord().getId())
                    .content(practice.getWord().getWord())
                    .url(practice.getUrl())
                    .hint(practice.createHint())
                    .isBookmarked(true)
                    .build();
        }
        else{
            result = ReadingProbResponseDto.builder()
                    .probId(practice.getId())
                    .type(typeOfRequestReadingProb)
                    .contentId(practice.getSentence().getId())
                    .content(practice.getSentence().getSentence())
                    .url(practice.getUrl())
                    .hint(practice.createHint())
                    .spacingInfo(practice.createSpacingInfo())
                    .isBookmarked(true)
                    .build();
        }
        return result;

    }
}
