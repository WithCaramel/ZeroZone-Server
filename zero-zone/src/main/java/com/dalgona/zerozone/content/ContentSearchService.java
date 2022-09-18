package com.dalgona.zerozone.content;

import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.content.domain.*;
import com.dalgona.zerozone.content.dto.LetterResponseDto;
import com.dalgona.zerozone.content.dto.SentenceResponseDto;
import com.dalgona.zerozone.content.dto.WordResponseDto;
import com.dalgona.zerozone.content.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContentSearchService {
    private final OnsetRepository onsetRepository;
    private final NucleusRepository nucleusRepository;
    private final LetterRepository letterRepository;
    private final WordRepository wordRepository;
    private final SituationRepository situationRepository;
    private final SentenceRepository sentenceRepository;

    @Transactional
    public List<Onset> getOnsetList(){
        List<Onset> onsetList = onsetRepository.findAll();
        Collections.sort(onsetList);
        return onsetList;
    }

    @Transactional
    public List<Nucleus> getNucleusList(Long onsetId) throws BadRequestException {
        Onset onset = getOnsetOrElseThrow(onsetId);
        List<Letter> letters = letterRepository.findAllByOnset(onset);
        List<Nucleus> nucleusList = extractUniqueNucleusList(letters);
        Collections.sort(nucleusList);
        return nucleusList;
    }

    private Onset getOnsetOrElseThrow(Long onsetId) {
        return onsetRepository.findById(onsetId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 초성입니다."));
    }

    private List<Nucleus> extractUniqueNucleusList(List<Letter> letters) {
        List<Nucleus> nucleusList = new ArrayList<>();
        for(Letter letter: letters){
            nucleusList.add(letter.getNucleus());
        }
        nucleusList = nucleusList.stream().distinct().collect(Collectors.toList());
        return nucleusList;
    }

    @Transactional
    public List<LetterResponseDto> getCodaList(Long onsetId, Long necleusId) {
        Onset onset = getOnsetOrElseThrow(onsetId);
        Nucleus nucleus = getNucleusOrElseThrow(necleusId);
        List<Letter> letters = letterRepository.findAllByOnsetAndNucleus(onset, nucleus);
        List<LetterResponseDto> letterResponseDtoList = extractLetterAndCodaList(letters);
        Collections.sort(letterResponseDtoList);
        return letterResponseDtoList;
    }

    private List<LetterResponseDto> extractLetterAndCodaList(List<Letter> letterList) {
        return letterList.stream()
                .map(l-> new LetterResponseDto(l))
                .collect(Collectors.toList());
    }

    private Nucleus getNucleusOrElseThrow(Long necleusId) {
        return nucleusRepository.findById(necleusId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 중성입니다."));
    }

    @Transactional
    public List<WordResponseDto> getWordList(Long onsetId) {
        Onset onset = getOnsetOrElseThrow(onsetId);
        List<Word> wordList = wordRepository.findAllByOnset(onset);
        List<WordResponseDto> wordResponseDtoList = getWordResponseDtoList(wordList);
        Collections.sort(wordResponseDtoList);
        return wordResponseDtoList;
    }

    private List<WordResponseDto> getWordResponseDtoList(List<Word> wordList) {
        return wordList.stream()
                .map(w-> new WordResponseDto(w))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Situation> getSituationList() {
        List<Situation> situationList = situationRepository.findAll();
        Collections.sort(situationList);
        return situationList;
    }

    @Transactional
    public List<SentenceResponseDto> getSentenceList(Long situationId) {
        Situation situation = getSituationOrElseThrow(situationId);
        List<Sentence> sentenceList = sentenceRepository.findAllBySituation(situation);
        List<SentenceResponseDto> sentenceResponseDtoList = getSentenceResponseDtoList(sentenceList);
        Collections.sort(sentenceResponseDtoList);
        return sentenceResponseDtoList;
    }

    private static List<SentenceResponseDto> getSentenceResponseDtoList(List<Sentence> sentenceList) {
        return sentenceList.stream()
                .map(s-> new SentenceResponseDto(s))
                .collect(Collectors.toList());
    }

    private Situation getSituationOrElseThrow(Long situationId) {
        return situationRepository.findById(situationId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 상황입니다."));
    }
}
