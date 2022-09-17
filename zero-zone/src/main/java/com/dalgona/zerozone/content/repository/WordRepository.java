package com.dalgona.zerozone.content.repository;

import com.dalgona.zerozone.content.domain.Onset;
import com.dalgona.zerozone.content.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findAllByOnset(Onset onset);

    Optional<Word> findByWord(String word);
}
