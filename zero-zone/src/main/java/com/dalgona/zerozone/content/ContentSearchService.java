package com.dalgona.zerozone.content;

import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.common.exception.InternalServerErrorCode;
import com.dalgona.zerozone.common.exception.InternalServerException;
import com.dalgona.zerozone.content.domain.*;
import com.dalgona.zerozone.content.dto.LetterResponseDto;
import com.dalgona.zerozone.content.dto.SentenceResponseDto;
import com.dalgona.zerozone.content.dto.WordResponseDto;
import com.dalgona.zerozone.content.repository.*;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import com.dalgona.zerozone.practice.repository.ReadingProbRepository;
import com.dalgona.zerozone.practice.repository.SpeakingProbRepository;
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
    private final SpeakingProbRepository speakingProbRepository;

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
    public List<LetterResponseDto> getCodaList(Long onsetId, Long nucleusId) {
        Onset onset = getOnsetOrElseThrow(onsetId);
        Nucleus nucleus = getNucleusOrElseThrow(nucleusId);
        List<Letter> letterList = letterRepository.findAllByOnsetAndNucleus(onset, nucleus);

        List<SpeakingProb> speakingProbList = getSpeakingProbFromLetter(letterList);
        List<LetterResponseDto> letterResponseDtoList = getLetterResponseDtoListOfSpeaking(speakingProbList);
        Collections.sort(letterResponseDtoList);
        return letterResponseDtoList;
    }

    private List<SpeakingProb> getSpeakingProbFromLetter(List<Letter> letterList) {
        List<SpeakingProb> speakingProbList = new ArrayList<>();
        for(Letter letter: letterList){
            speakingProbList.add(speakingProbRepository.findByLetter(letter)
                    .orElseThrow(()-> new InternalServerException(InternalServerErrorCode.NOT_FOUND, "해당 문장은 연습 문제로 등록되지 않았습니다.")));
        }
        return speakingProbList;
    }

    private static List<LetterResponseDto> getLetterResponseDtoListOfSpeaking(List<SpeakingProb> speakingProbList) {
        return speakingProbList.stream()
                .map(s-> new LetterResponseDto(s))
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
        List<SpeakingProb> speakingProbList = getSpeakingProbFromWord(wordList);
        List<WordResponseDto> wordResponseDtoList = getWordResponseDtoList(speakingProbList);
        Collections.sort(wordResponseDtoList);
        return wordResponseDtoList;
    }

    private List<SpeakingProb> getSpeakingProbFromWord(List<Word> wordList) {
        List<SpeakingProb> speakingProbList = new ArrayList<>();
        for(Word word: wordList){
            speakingProbList.add(speakingProbRepository.findByWord(word)
                    .orElseThrow(()-> new InternalServerException(InternalServerErrorCode.NOT_FOUND, "해당 문장은 연습 문제로 등록되지 않았습니다.")));
        }
        return speakingProbList;
    }

    private List<WordResponseDto> getWordResponseDtoList(List<SpeakingProb> speakingProbList) {
        return speakingProbList.stream()
                .map(s-> new WordResponseDto(s))
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

        List<SpeakingProb> speakingProbList = getSpeakingProbFromSentence(sentenceList);
        List<SentenceResponseDto> sentenceResponseDtoList = getSentenceResponseDtoList(speakingProbList);
        Collections.sort(sentenceResponseDtoList);
        return sentenceResponseDtoList;
    }

    private List<SpeakingProb> getSpeakingProbFromSentence(List<Sentence> sentenceList) {
        List<SpeakingProb> speakingProbList = new ArrayList<>();
        for(Sentence sentence: sentenceList){
            speakingProbList.add(speakingProbRepository.findBySentence(sentence)
                    .orElseThrow(()-> new InternalServerException(InternalServerErrorCode.NOT_FOUND, "해당 문장은 연습 문제로 등록되지 않았습니다.")));
        }
        return speakingProbList;
    }

    private static List<SentenceResponseDto> getSentenceResponseDtoList(List<SpeakingProb> speakingProbList) {
        return speakingProbList.stream()
                .map(s-> new SentenceResponseDto(s))
                .collect(Collectors.toList());
    }

    private Situation getSituationOrElseThrow(Long situationId) {
        return situationRepository.findById(situationId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 상황입니다."));
    }
}
