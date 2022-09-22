package com.dalgona.zerozone.exam.repository;

import com.dalgona.zerozone.exam.domain.Exam;
import com.dalgona.zerozone.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    int countByMember(Member member);
}
