package com.dalgona.zerozone.exam.dto;

import com.dalgona.zerozone.hangulAnalyzer.SpacingInfoCreator;
import com.dalgona.zerozone.hangulAnalyzer.UnicodeHandler;
import com.dalgona.zerozone.practice.domain.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadingProbAndBookmarkJoin {
    Long readingProbId;
    ContentType type;
    String url;
    Long wordId;
    String word;
    Long sentenceId;
    String sentence;
    Long bookmarkReadingProbId;

    public String createHint() {
        String content;
        if(type == ContentType.WORD) content = word;
        else content = sentence;
        return UnicodeHandler.splitHangeulToOnset(content);
    }

    public String createSpacingInfo() {
        if(type != ContentType.SENTENCE) return null;
        return SpacingInfoCreator.createSpacingInfo(sentence);
    }
}
