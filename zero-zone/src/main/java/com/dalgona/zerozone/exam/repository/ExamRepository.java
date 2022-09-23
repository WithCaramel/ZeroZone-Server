package com.dalgona.zerozone.exam.repository;

import com.dalgona.zerozone.exam.domain.Exam;
import com.dalgona.zerozone.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    int countByMember(Member member);
    Page<Exam> findAllByMember(Member member, Pageable paging);
}
