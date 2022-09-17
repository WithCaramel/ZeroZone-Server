package com.dalgona.zerozone.content.repository;

import com.dalgona.zerozone.content.domain.Situation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SituationRepository extends JpaRepository<Situation, Long> {
    Optional<Situation> findBySituation(String s);
}
