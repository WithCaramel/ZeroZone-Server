package com.dalgona.zerozone.practice.repository;

import com.dalgona.zerozone.content.domain.Letter;
import com.dalgona.zerozone.content.domain.Sentence;
import com.dalgona.zerozone.content.domain.Word;
import com.dalgona.zerozone.practice.domain.ContentType;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpeakingProbRepository extends JpaRepository<SpeakingProb, Long> {
    Optional<SpeakingProb> findByTypeAndLetter(ContentType type, Letter letter);
    Optional<SpeakingProb> findByTypeAndWord(ContentType type, Word word);
    Optional<SpeakingProb> findByTypeAndSentence(ContentType type, Sentence sentence);

    Optional<SpeakingProb> findByLetter(Letter letter);
    Optional<SpeakingProb> findByWord(Word word);
    Optional<SpeakingProb> findBySentence(Sentence sentence);
}
