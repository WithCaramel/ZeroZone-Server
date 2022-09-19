package com.dalgona.zerozone.practice.controller;

import com.dalgona.zerozone.content.ContentSearchService;
import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.domain.Situation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/reading-practices")
public class ReadingPracticeController {
    public final ContentSearchService contentSearchService;

    @GetMapping("/word/onset")
    public List<Onset> getOnsetList(){
        return contentSearchService.getOnsetList();
    }

    @GetMapping("/sentence/situation")
    public List<Situation> getSituationList(){
        return contentSearchService.getSituationList();
    }


}
