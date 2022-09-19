package com.dalgona.zerozone.practice.controller;

import com.dalgona.zerozone.content.ContentSearchService;
import com.dalgona.zerozone.content.domain.Nucleus;
import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.domain.Situation;
import com.dalgona.zerozone.content.dto.LetterResponseDto;
import com.dalgona.zerozone.content.dto.SentenceResponseDto;
import com.dalgona.zerozone.content.dto.WordResponseDto;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import com.dalgona.zerozone.practice.dto.SpeakingProbResponseDto;
import com.dalgona.zerozone.practice.service.SpeakingLetterProbService;
import com.dalgona.zerozone.practice.service.SpeakingSentenceProbService;
import com.dalgona.zerozone.practice.service.SpeakingWordProbService;
import lombok.RequiredArgsConstructor;
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
    public SpeakingProbResponseDto getSpeakingLetterProb(@PathVariable Long letterId){
        return SpeakingProbResponseDto.of(letterProbService.getPractice(letterId));
    }

    @GetMapping("/letter/random")
    public SpeakingProbResponseDto getSpeakingLetterProbRandomly(@RequestParam Long onsetId){
        return SpeakingProbResponseDto.of(letterProbService.getRandomPractice(onsetId));
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
    public SpeakingProbResponseDto getSpeakingWordProb(@PathVariable Long wordId){
        return SpeakingProbResponseDto.of(wordProbService.getPractice(wordId));
    }

    @GetMapping("/word/random")
    public SpeakingProbResponseDto getSpeakingWordProbRandomly(@RequestParam Long onsetId){
        return SpeakingProbResponseDto.of(wordProbService.getRandomPractice(onsetId));
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
    public SpeakingProbResponseDto getSpeakingSentenceProb(@PathVariable Long sentenceId){
        return SpeakingProbResponseDto.of(sentenceProbService.getPractice(sentenceId));
    }

    @GetMapping("/sentence/random")
    public SpeakingProbResponseDto getSpeakingSentenceProbRandomly(@RequestParam Long situationId){
        return SpeakingProbResponseDto.of(sentenceProbService.getRandomPractice(situationId));
    }

}
