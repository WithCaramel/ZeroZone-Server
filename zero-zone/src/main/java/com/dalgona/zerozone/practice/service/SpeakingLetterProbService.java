package com.dalgona.zerozone.practice.service;

import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.common.exception.InternalServerErrorCode;
import com.dalgona.zerozone.common.exception.InternalServerException;
import com.dalgona.zerozone.content.domain.Letter;
import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.repository.LetterRepository;
import com.dalgona.zerozone.content.repository.OnsetRepository;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import com.dalgona.zerozone.practice.repository.SpeakingProbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakingLetterProbService implements PracticeSearchInterface {
    private final OnsetRepository onsetRepository;
    private final LetterRepository letterRepository;
    private final SpeakingProbRepository speakingProbRepository;

    @Transactional
    @Override
    public SpeakingProb getPractice(Long speakingProbId) throws BadRequestException {
        return getSpeakingProbOrElseThrow(speakingProbId);
    }

    private SpeakingProb getSpeakingProbOrElseThrow(Long speakingProbId) {
        return speakingProbRepository.findById(speakingProbId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 발음 연습 문제를 조회했습니다."));
    }

    @Transactional
    @Override
    public SpeakingProb getRandomPractice(Long onsetId) throws BadRequestException, InternalServerException {
        Onset onset = getOnsetOrElseThrow(onsetId);
        List<Letter> letterList = letterRepository.findAllByOnset(onset);
        Letter randomLetter = getLetterRandomly(letterList);
        return getSpeakingProbByLetterOrElseThrow(randomLetter);
    }

    private Onset getOnsetOrElseThrow(Long onsetId) {
        return onsetRepository.findById(onsetId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 초성입니다."));
    }

    private Letter getLetterRandomly(List<Letter> letterList) {
        int randomIndex = (int)(Math.random() * letterList.size());
        return letterList.get(randomIndex);
    }

    private SpeakingProb getSpeakingProbByLetterOrElseThrow(Letter letter) {
        return speakingProbRepository.findByLetter(letter)
                .orElseThrow(() -> new InternalServerException(InternalServerErrorCode.NOT_FOUND, "존재하지 않는 발음 연습 문제를 조회했습니다."));
    }
}
