package com.dalgona.zerozone.practice.repository;

import com.dalgona.zerozone.content.domain.Sentence;
import com.dalgona.zerozone.content.domain.Word;
import com.dalgona.zerozone.practice.domain.ContentType;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadingProbRepository extends JpaRepository<ReadingProb, Long> {
    Optional<ReadingProb> findByTypeAndWord(ContentType type, Word word);
    Optional<ReadingProb> findByTypeAndSentence(ContentType type, Sentence sentence);
    List<ReadingProb> findAllByType(ContentType type);

    Optional<ReadingProb> findBySentence(Sentence sentence);
    Optional<ReadingProb> findByWord(Word word);
}

