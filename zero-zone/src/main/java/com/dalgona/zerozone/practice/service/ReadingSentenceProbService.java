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
import com.dalgona.zerozone.practice.repository.ReadingProbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingSentenceProbService implements PracticeSearchInterface {

    private final SituationRepository situationRepository;
    private final SentenceRepository sentenceRepository;
    private final ReadingProbRepository readingProbRepository;

    @Transactional
    @Override
    public ReadingProb getPractice(Long readingProbId) throws BadRequestException {
        return getReadingProbBySentenceOrElseThrow(readingProbId);
    }

    private ReadingProb getReadingProbBySentenceOrElseThrow(Long readingProbId) {
        return readingProbRepository.findById(readingProbId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 구화 연습 문제입니다."));
    }

    @Transactional
    @Override
    public ReadingProb getRandomPractice(Long situationId) throws BadRequestException, InternalServerException {
        Situation situation = getSituationOrElseThrow(situationId);
        List<Sentence> sentenceList = sentenceRepository.findAllBySituation(situation);
        Sentence sentence = getSentenceRandomly(sentenceList);
        return getReadingProbBySentenceOrElseThrow(sentence);
    }

    private Situation getSituationOrElseThrow(Long situationId) {
        return situationRepository.findById(situationId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 상황입니다."));
    }

    private Sentence getSentenceRandomly(List<Sentence> sentenceList) {
        int randomIndex = (int)(Math.random() * sentenceList.size());
        return sentenceList.get(randomIndex);
    }

    private ReadingProb getReadingProbBySentenceOrElseThrow(Sentence sentence) {
        return readingProbRepository.findBySentence(sentence)
                .orElseThrow(() -> new InternalServerException(InternalServerErrorCode.NOT_FOUND, "존재하지 않는 구화 연습 문제를 조회했습니다."));
    }

}
