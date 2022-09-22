package com.dalgona.zerozone.exam.domain;

import com.dalgona.zerozone.common.dto.BaseTimeEntity;
import com.dalgona.zerozone.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exam extends BaseTimeEntity {
    @Id
    @Column(name = "EXAM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String examName;

    @Column(nullable = false)
    private int probCount;

    @Column(nullable = false)
    private int correctCount;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @JsonManagedReference
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<ExamProb> examProbList = new ArrayList<>();

    @Builder
    public Exam(String examName, int probCount, int correctCount, Member member){
        this.examName = examName;
        this.probCount = probCount;
        this.correctCount = correctCount;
        this.member = member;
    }

    public Exam updateExamName(String newName){
        this.examName = newName;
        return this;
    }

    public Exam updateCorrectCount(int correctCount){
        this.correctCount = correctCount;
        return this;
    }

    public Exam updateExamProb(List<ExamProb> examProbList){
        this.examProbList = examProbList;
        return this;
    }
}
