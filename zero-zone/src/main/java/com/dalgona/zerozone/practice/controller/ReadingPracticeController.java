package com.dalgona.zerozone.practice.controller;

import com.dalgona.zerozone.content.ContentSearchService;
import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.domain.Situation;
import com.dalgona.zerozone.practice.dto.ReadingProbResponseDto;
import com.dalgona.zerozone.practice.service.ReadingSentenceProbService;
import com.dalgona.zerozone.practice.service.ReadingWordProbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/reading-practices")
public class ReadingPracticeController {
    public final ContentSearchService contentSearchService;
    private final ReadingWordProbService readingWordProbService;
    private final ReadingSentenceProbService readingSentenceProbService;

    @GetMapping("/word/onset")
    public List<Onset> getOnsetList(){
        return contentSearchService.getOnsetList();
    }

    @GetMapping("/word/{wordId}")
    public ReadingProbResponseDto getReadingWordProb(@PathVariable Long wordId){
        return ReadingProbResponseDto.of(readingWordProbService.getPractice(wordId));
    }

    @GetMapping("/word/random")
    public ReadingProbResponseDto getReadingWordProbRandomly(@RequestParam Long onsetId){
        return ReadingProbResponseDto.of(readingWordProbService.getRandomPractice(onsetId));
    }

    @GetMapping("/sentence/situation")
    public List<Situation> getSituationList(){
        return contentSearchService.getSituationList();
    }

    @GetMapping("/sentence/{sentenceId}")
    public ReadingProbResponseDto getReadingSentenceProb(@PathVariable Long sentenceId){
        return ReadingProbResponseDto.of(readingSentenceProbService.getPractice(sentenceId));
    }

    @GetMapping("/sentence/random")
    public ReadingProbResponseDto getReadingSentenceProbRandomly(@RequestParam Long situationId){
        return ReadingProbResponseDto.of(readingSentenceProbService.getRandomPractice(situationId));
    }


}
