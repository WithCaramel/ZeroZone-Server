package com.dalgona.zerozone.content.repository;

import com.dalgona.zerozone.content.domain.Nucleus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NucleusRepository extends JpaRepository<Nucleus, Long> {
    Optional<Nucleus> findByNucleus(String s);
}

