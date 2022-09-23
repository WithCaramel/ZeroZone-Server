package com.dalgona.zerozone.practice.service;

import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.common.exception.InternalServerErrorCode;
import com.dalgona.zerozone.common.exception.InternalServerException;
import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.domain.Word;
import com.dalgona.zerozone.content.repository.OnsetRepository;
import com.dalgona.zerozone.content.repository.WordRepository;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import com.dalgona.zerozone.practice.repository.SpeakingProbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakingWordProbService implements PracticeSearchInterface {
    private final OnsetRepository onsetRepository;
    private final WordRepository wordRepository;
    private final SpeakingProbRepository speakingProbRepository;

    @Transactional
    @Override
    public SpeakingProb getPractice(Long speakingProbId) throws BadRequestException, InternalServerException {
        return getSpeakingProbOrElseThrow(speakingProbId);
    }

    private SpeakingProb getSpeakingProbOrElseThrow(Long speakingProbId) {
        return speakingProbRepository.findById(speakingProbId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 발음 연습 문제를 조회했습니다."));
    }

    @Transactional
    @Override
    public SpeakingProb getRandomPractice(Long onsetId) throws BadRequestException {
        Onset onset = getOnsetOrElseThrow(onsetId);
        List<Word> wordList = wordRepository.findAllByOnset(onset);
        Word randomWord = getWordRandomly(wordList);
        return getSpeakingProbByWordOrElseThrow(randomWord);
    }

    private Onset getOnsetOrElseThrow(Long onsetId) {
        return onsetRepository.findById(onsetId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 초성입니다."));
    }

    private Word getWordRandomly(List<Word> wordList) {
        int randomIndex = (int)(Math.random() * wordList.size());
        return wordList.get(randomIndex);
    }

    private SpeakingProb getSpeakingProbByWordOrElseThrow(Word word) {
        return speakingProbRepository.findByWord(word)
                .orElseThrow(() -> new InternalServerException(InternalServerErrorCode.NOT_FOUND, "존재하지 않는 발음 연습 문제를 조회했습니다."));
    }
}
