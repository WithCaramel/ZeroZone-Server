package com.dalgona.zerozone.practice.domain;

import com.dalgona.zerozone.content.domain.Letter;
import com.dalgona.zerozone.content.domain.Sentence;
import com.dalgona.zerozone.content.domain.Word;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class SpeakingProb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPEAKINGPROB_ID")
    private Long id;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentType type;

    @Column
    private String url;

    @OneToOne
    @JoinColumn(name = "LETTER_ID")
    private Letter letter;

    @OneToOne
    @JoinColumn(name = "WORD_ID")
    private Word word;

    @OneToOne
    @JoinColumn(name = "SENTENCE_ID")
    private Sentence sentence;

}
