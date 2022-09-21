package com.dalgona.zerozone.practice.controller;

import com.dalgona.zerozone.bookmark.service.BookmarkReadingService;
import com.dalgona.zerozone.content.ContentSearchService;
import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.domain.Situation;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.dto.ReadingProbResponseDto;
import com.dalgona.zerozone.practice.service.ReadingSentenceProbService;
import com.dalgona.zerozone.practice.service.ReadingWordProbService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/reading-practices")
public class ReadingPracticeController {
    public final ContentSearchService contentSearchService;
    private final ReadingWordProbService readingWordProbService;
    private final ReadingSentenceProbService readingSentenceProbService;
    private final BookmarkReadingService bookmarkReadingService;

    @GetMapping("/word/onset")
    public List<Onset> getOnsetList(){
        return contentSearchService.getOnsetList();
    }

    @GetMapping("/word/{wordId}")
    public ReadingProbResponseDto getReadingWordProb(
            @PathVariable Long wordId,
            @AuthenticationPrincipal Member member){
        ReadingProb practice = readingWordProbService.getPractice(wordId);
        bookmarkReadingService.setMember(member);
        boolean isBookmarked = bookmarkReadingService.isBookmarked(practice.getId());
        return ReadingProbResponseDto.of(practice, isBookmarked);
    }

    @GetMapping("/word/random")
    public ReadingProbResponseDto getReadingWordProbRandomly(
            @RequestParam Long onsetId,
            @AuthenticationPrincipal Member member){
        ReadingProb practice = readingWordProbService.getRandomPractice(onsetId);
        bookmarkReadingService.setMember(member);
        boolean isBookmarked = bookmarkReadingService.isBookmarked(practice.getId());
        return ReadingProbResponseDto.of(practice, isBookmarked);
    }

    @GetMapping("/sentence/situation")
    public List<Situation> getSituationList(){
        return contentSearchService.getSituationList();
    }

    @GetMapping("/sentence/{sentenceId}")
    public ReadingProbResponseDto getReadingSentenceProb(
            @PathVariable Long sentenceId,
            @AuthenticationPrincipal Member member){
        ReadingProb practice = readingSentenceProbService.getPractice(sentenceId);
        bookmarkReadingService.setMember(member);
        boolean isBookmarked = bookmarkReadingService.isBookmarked(practice.getId());
        return ReadingProbResponseDto.of(practice, isBookmarked);
    }

    @GetMapping("/sentence/random")
    public ReadingProbResponseDto getReadingSentenceProbRandomly(
            @RequestParam Long situationId,
            @AuthenticationPrincipal Member member){
        ReadingProb practice = readingSentenceProbService.getRandomPractice(situationId);
        bookmarkReadingService.setMember(member);
        boolean isBookmarked = bookmarkReadingService.isBookmarked(practice.getId());
        return ReadingProbResponseDto.of(practice, isBookmarked);
    }


}
