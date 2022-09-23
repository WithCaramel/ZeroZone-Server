package com.dalgona.zerozone.exam.repository;

import com.dalgona.zerozone.exam.domain.Exam;
import com.dalgona.zerozone.exam.domain.ExamProb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamProbRepository extends JpaRepository<ExamProb, Long> {
    Page<ExamProb> findAllByExam(Exam exam, PageRequest paging);
}
