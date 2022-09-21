package com.dalgona.zerozone.bookmark.repository;

import com.dalgona.zerozone.bookmark.domain.BookmarkedReadingProb;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkedReadingProbRepository extends JpaRepository<BookmarkedReadingProb, Long> {
    Optional<BookmarkedReadingProb> findByMemberAndReadingProb(Member member, ReadingProb readingProb);
    Page<BookmarkedReadingProb> findAllByMember(Member member, Pageable paging);
    List<BookmarkedReadingProb> findAllByMember(Member member);
    void deleteByReadingProbAndMember(ReadingProb readingProb, Member member);

    boolean existsByReadingProbAndMember(ReadingProb readingProb, Member member);
    Long countByMember(Member member);
}
