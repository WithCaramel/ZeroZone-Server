package com.dalgona.zerozone.content.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SENTENCE_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "SITUATION_ID")
    private Situation situation;

    @Column(length = 200, nullable = false, unique = true)
    private String sentence;

    @Builder
    public Sentence(Situation situation, String sentence) {
        this.sentence = sentence;
        this.situation = situation;
    }
}

