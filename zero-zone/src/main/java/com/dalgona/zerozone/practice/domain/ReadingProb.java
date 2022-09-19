package com.dalgona.zerozone.practice.domain;

import com.dalgona.zerozone.content.domain.Sentence;
import com.dalgona.zerozone.content.domain.Word;
import com.dalgona.zerozone.hangulAnalyzer.SpacingInfoCreator;
import com.dalgona.zerozone.hangulAnalyzer.UnicodeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReadingProb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "READINGPROB_ID")
    private Long id;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentType type;

    @Column
    private String url;

    @OneToOne
    @JoinColumn(name = "WORD_ID")
    private Word word;

    @OneToOne
    @JoinColumn(name = "SENTENCE_ID")
    private Sentence sentence;

    @Builder
    public ReadingProb(ContentType type, String hint, String spacingInfo, String url, Word word, Sentence sentence){
        this.type = type;
        this.url = url;
        this.sentence = sentence;
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadingProb that = (ReadingProb) o;
        boolean is_same = id.equals(that.id) && Objects.equals(type, that.type) && word.equals(that.word) && Objects.equals(sentence, that.sentence);
        return is_same;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, url, word, sentence);
    }

    public String createHint() {
        String content;
        if(type == ContentType.WORD) content = word.getWord();
        else content = sentence.getSentence();
        return UnicodeHandler.splitHangeulToOnset(content);
    }

    public String createSpacingInfo() {
        if(type != ContentType.SENTENCE) return null;
        String sentence = this.sentence.getSentence();
        return SpacingInfoCreator.createSpacingInfo(sentence);
    }
}
