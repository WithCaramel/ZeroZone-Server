package com.dalgona.zerozone.content.repository;

import com.dalgona.zerozone.content.domain.Onset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OnsetRepository extends JpaRepository<Onset, Long> {
    Optional<Onset> findByOnset(String s);
}

