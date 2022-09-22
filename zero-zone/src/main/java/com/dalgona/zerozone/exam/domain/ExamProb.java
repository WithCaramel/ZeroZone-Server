package com.dalgona.zerozone.exam.domain;

import com.dalgona.zerozone.exam.dto.ExamResult;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExamProb {

    @Id
    @Column(name = "EXAMPROB_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int index;

    @Column
    private boolean usedHint;

    @Column
    private boolean isCorrect;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;

    @OneToOne
    @JoinColumn(name = "READINGPROB_ID")
    private ReadingProb readingProb;

    @Builder
    public ExamProb(int index, boolean usedHint, boolean isCorrect, Exam exam, ReadingProb readingProb){
        this.index = index;
        this.usedHint = usedHint;
        this.isCorrect = isCorrect;
        this.exam = exam;
        this.readingProb = readingProb;
    }

    public ExamProb updateResult(ExamResult examResult){
        this.usedHint = examResult.isUsedHint();
        this.isCorrect = examResult.isCorrect();
        return this;
    }

}
