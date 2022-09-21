package com.dalgona.zerozone.bookmark.repository;

import com.dalgona.zerozone.bookmark.domain.BookmarkedSpeakingProb;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkedSpeakingProbRepository extends JpaRepository<BookmarkedSpeakingProb, Long> {
    Optional<BookmarkedSpeakingProb> findByMemberAndSpeakingProb(Member member, SpeakingProb speakingProb);
    Page<BookmarkedSpeakingProb> findAllByMember(Member member, Pageable paging);
    List<BookmarkedSpeakingProb> findAllByMember(Member member);
    void deleteBySpeakingProbAndMember(SpeakingProb speakingProb, Member member);
    boolean existsBySpeakingProbAndMember(SpeakingProb speakingProb, Member member);
    Long countByMember(Member member);
}
