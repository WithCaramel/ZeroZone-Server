package com.dalgona.zerozone.content.repository;

import com.dalgona.zerozone.content.domain.Coda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodaRepository extends JpaRepository<Coda, Long> {
    Optional<Coda> findByCoda(String s);
}
