package com.dalgona.zerozone.content.repository;

import com.dalgona.zerozone.content.domain.Letter;
import com.dalgona.zerozone.content.domain.Nucleus;
import com.dalgona.zerozone.content.domain.Onset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    List<Letter> findAllByOnset(Onset onset);
    List <Letter> findAllByOnsetAndNucleus(Onset onset, Nucleus nucleus);
    Optional<Letter> findByLetter(String token);
}

