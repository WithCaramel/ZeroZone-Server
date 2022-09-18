package com.dalgona.zerozone.content;

import com.dalgona.zerozone.content.domain.Nucleus;
import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.domain.Situation;
import com.dalgona.zerozone.content.dto.LetterResponseDto;
import com.dalgona.zerozone.content.dto.SentenceResponseDto;
import com.dalgona.zerozone.content.dto.WordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/speaking-practices")
public class SpeakingPracticeController {
    public final ContentSearchService contentSearchService;

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
            @RequestParam Long necleusId
    ){
        return contentSearchService.getCodaList(onsetId, necleusId);
    }

    @GetMapping("/word/onset")
    public List<Onset> getOnsetListOfWord(){
        return contentSearchService.getOnsetList();
    }

    @GetMapping("/word")
    public List<WordResponseDto> getWordList(@RequestParam Long onsetId){
        return contentSearchService.getWordList(onsetId);
    }

    @GetMapping("/sentence/situation")
    public List<Situation> getSituationList(){
        return contentSearchService.getSituationList();
    }

    @GetMapping("/sentence")
    public List<SentenceResponseDto> getSentenceList(@RequestParam Long situationId){
        return contentSearchService.getSentenceList(situationId);
    }

}
