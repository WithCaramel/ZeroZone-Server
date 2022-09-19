package com.dalgona.zerozone.practice.service;

import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.common.exception.InternalServerErrorCode;
import com.dalgona.zerozone.common.exception.InternalServerException;
import com.dalgona.zerozone.content.domain.Sentence;
import com.dalgona.zerozone.content.domain.Situation;
import com.dalgona.zerozone.content.repository.SentenceRepository;
import com.dalgona.zerozone.content.repository.SituationRepository;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import com.dalgona.zerozone.practice.repository.SpeakingProbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakingSentenceProbService implements PracticeSearchInterface {

    private final SituationRepository situationRepository;
    private final SentenceRepository sentenceRepository;
    private final SpeakingProbRepository speakingProbRepository;


    @Transactional
    @Override
    public SpeakingProb getPractice(Long sentenceId) throws BadRequestException, InternalServerException {
        Sentence sentence = getSentenceOrElseThrow(sentenceId);
        return getSpeakingProbOrElseThrow(sentence);
    }

    private Sentence getSentenceOrElseThrow(Long sentenceId) {
        return sentenceRepository.findById(sentenceId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 문장입니다."));
    }

    private SpeakingProb getSpeakingProbOrElseThrow(Sentence sentence) {
        return speakingProbRepository.findBySentence(sentence)
                .orElseThrow(() -> new InternalServerException(InternalServerErrorCode.NOT_FOUND, "존재하지 않는 구화 연습 문제를 조회했습니다."));
    }

    @Transactional
    @Override
    public SpeakingProb getRandomPractice(Long situationId) throws BadRequestException {
        Situation situation = getSituationOrElseThrow                                                                                                                 (situationId);
        List<Sentence> sentenceList = sentenceRepository.findAllBySituation(situation);
        Sentence sentence = getSentenceRandomly(sentenceList);
        return getSpeakingProbOrElseThrow(sentence);
    }

    private Situation getSituationOrElseThrow(Long situationId) {
        return situationRepository.findById(situationId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 상황입니다."));
    }

    private Sentence getSentenceRandomly(List<Sentence> sentenceList) {
        int randomIndex = (int)(Math.random() * sentenceList.size());
        return sentenceList.get(randomIndex);
    }
}
