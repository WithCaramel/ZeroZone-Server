package com.dalgona.zerozone.practice.controller;

import com.dalgona.zerozone.bookmark.service.BookmarkSpeakingService;
import com.dalgona.zerozone.content.ContentSearchService;
import com.dalgona.zerozone.content.domain.Nucleus;
import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.domain.Situation;
import com.dalgona.zerozone.content.dto.LetterResponseDto;
import com.dalgona.zerozone.content.dto.SentenceResponseDto;
import com.dalgona.zerozone.content.dto.WordResponseDto;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import com.dalgona.zerozone.practice.dto.SpeakingProbResponseDto;
import com.dalgona.zerozone.practice.service.SpeakingLetterProbService;
import com.dalgona.zerozone.practice.service.SpeakingSentenceProbService;
import com.dalgona.zerozone.practice.service.SpeakingWordProbService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/speaking-practices")
public class SpeakingPracticeController {
    public final ContentSearchService contentSearchService;
    private final SpeakingLetterProbService letterProbService;
    private final SpeakingWordProbService wordProbService;
    private final SpeakingSentenceProbService sentenceProbService;
    private final BookmarkSpeakingService bookmarkSpeakingService;

    @GetMapping("/letter/onset")
    public List<Onset> getOnsetListOfLetter(){
        return contentSearchService.getOnsetList();
    }

    @GetMapping("/letter/nucleus")
    public List<Nucleus> getNucleusListOfLetter(
            @RequestParam(name = "onsetId") Long onsetId
    ){
        return contentSearchService.getNucleusList(onsetId);
    }

    @GetMapping("/letter/coda")
    public List<LetterResponseDto> getCodaListOfLetter(
            @RequestParam Long onsetId,
            @RequestParam Long nucleusId
    ){
        return contentSearchService.getCodaList(onsetId, nucleusId);
    }

    @GetMapping("/letter/{letterId}")
    public SpeakingProbResponseDto getSpeakingLetterProb(
            @PathVariable Long letterId,
            @AuthenticationPrincipal Member member){
        SpeakingProb practice = letterProbService.getPractice(letterId);
        bookmarkSpeakingService.setMember(member);
        boolean isBookmarked = bookmarkSpeakingService.isBookmarked(practice.getId());
        return SpeakingProbResponseDto.of(practice, isBookmarked);
    }

    @GetMapping("/letter/random")
    public SpeakingProbResponseDto getSpeakingLetterProbRandomly(
            @RequestParam Long onsetId,
            @AuthenticationPrincipal Member member){
        SpeakingProb practice = letterProbService.getRandomPractice(onsetId);
        bookmarkSpeakingService.setMember(member);
        boolean isBookmarked = bookmarkSpeakingService.isBookmarked(practice.getId());
        return SpeakingProbResponseDto.of(practice, isBookmarked);
    }

    @GetMapping("/word/onset")
    public List<Onset> getOnsetListOfWord(){
        return contentSearchService.getOnsetList();
    }

    @GetMapping("/word")
    public List<WordResponseDto> getWordList(@RequestParam Long onsetId){
        return contentSearchService.getWordList(onsetId);
    }

    @GetMapping("/word/{wordId}")
    public SpeakingProbResponseDto getSpeakingWordProb(
            @PathVariable Long wordId,
            @AuthenticationPrincipal Member member){
        SpeakingProb practice = wordProbService.getPractice(wordId);
        bookmarkSpeakingService.setMember(member);
        boolean isBookmarked = bookmarkSpeakingService.isBookmarked(practice.getId());
        return SpeakingProbResponseDto.of(practice, isBookmarked);
    }

    @GetMapping("/word/random")
    public SpeakingProbResponseDto getSpeakingWordProbRandomly(
            @RequestParam Long onsetId,
            @AuthenticationPrincipal Member member){
        SpeakingProb practice = wordProbService.getRandomPractice(onsetId);
        bookmarkSpeakingService.setMember(member);
        boolean isBookmarked = bookmarkSpeakingService.isBookmarked(practice.getId());
        return SpeakingProbResponseDto.of(practice, isBookmarked);
    }

    @GetMapping("/sentence/situation")
    public List<Situation> getSituationList(){
        return contentSearchService.getSituationList();
    }

    @GetMapping("/sentence")
    public List<SentenceResponseDto> getSentenceList(@RequestParam Long situationId){
        return contentSearchService.getSentenceList(situationId);
    }

    @GetMapping("/sentence/{sentenceId}")
    public SpeakingProbResponseDto getSpeakingSentenceProb(
            @PathVariable Long sentenceId,
            @AuthenticationPrincipal Member member){
        SpeakingProb practice = sentenceProbService.getPractice(sentenceId);
        bookmarkSpeakingService.setMember(member);
        boolean isBookmarked = bookmarkSpeakingService.isBookmarked(practice.getId());
        return SpeakingProbResponseDto.of(practice, isBookmarked);
    }

    @GetMapping("/sentence/random")
    public SpeakingProbResponseDto getSpeakingSentenceProbRandomly(
            @RequestParam Long situationId,
            @AuthenticationPrincipal Member member){
        SpeakingProb practice = sentenceProbService.getRandomPractice(situationId);
        bookmarkSpeakingService.setMember(member);
        boolean isBookmarked = bookmarkSpeakingService.isBookmarked(practice.getId());
        return SpeakingProbResponseDto.of(practice, isBookmarked);
    }

}
