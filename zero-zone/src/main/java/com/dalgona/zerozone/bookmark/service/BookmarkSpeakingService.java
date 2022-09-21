package com.dalgona.zerozone.bookmark.service;

import com.dalgona.zerozone.bookmark.domain.BookmarkedSpeakingProb;
import com.dalgona.zerozone.bookmark.dto.BookmarkedSpeakingProbResponseDto;
import com.dalgona.zerozone.bookmark.repository.BookmarkedSpeakingProbRepository;
import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import com.dalgona.zerozone.practice.repository.SpeakingProbRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookmarkSpeakingService implements BookmarkServiceInterface {
    private final SpeakingProbRepository speakingProbRepository;
    private final BookmarkedSpeakingProbRepository bookmarkedSpeakingProbRepository;
    @Setter private Member member;
    final int PAGE_SIZE = 10;

    @Transactional
    @Override
    public void addBookmark(Long speakingProbId) {
        SpeakingProb speakingProb = getSpeakingProbOrElseThrow(speakingProbId);
        BookmarkedSpeakingProb bookmarkedSpeakingProb = getBookmarkedSpeakingProbToSave(speakingProb);
        bookmarkedSpeakingProbRepository.save(bookmarkedSpeakingProb);
    }

    private SpeakingProb getSpeakingProbOrElseThrow(Long speakingProbId) {
        return speakingProbRepository.findById(speakingProbId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 발음 연습 문제입니다."));
    }

    private BookmarkedSpeakingProb getBookmarkedSpeakingProbToSave(SpeakingProb speakingProb) {
        Optional<BookmarkedSpeakingProb> optionalBookmarkedSpeakingProb = bookmarkedSpeakingProbRepository.findByMemberAndSpeakingProb(member, speakingProb);
        if(optionalBookmarkedSpeakingProb.isPresent()) throw new BadRequestException(BadRequestErrorCode.DUPLICATED, "이미 북마크로 등록되었습니다.");
        return createBookmarkedSpeakingProb(speakingProb);
    }

    private BookmarkedSpeakingProb createBookmarkedSpeakingProb(SpeakingProb speakingProb) {
        return BookmarkedSpeakingProb.builder()
                .speakingProb(speakingProb)
                .member(member)
                .build();
    }

    @Transactional
    @Override
    public void unBookmark(Long speakingProbId) {
        SpeakingProb speakingProb = getSpeakingProbOrElseThrow(speakingProbId);
        checkSpeakingProbBookmarked(speakingProb);
        bookmarkedSpeakingProbRepository.deleteBySpeakingProbAndMember(speakingProb, member);
    }

    private void checkSpeakingProbBookmarked(SpeakingProb speakingProb) {
        if(!bookmarkedSpeakingProbRepository.existsBySpeakingProbAndMember(speakingProb, member))
            throw new BadRequestException(BadRequestErrorCode.NOT_FOUND, "북마크로 등록되지 않은 발음 연습 문제입니다.");
    }

    @Transactional
    @Override
    public Page<BookmarkedSpeakingProbResponseDto> getBookmarkListWithPage(int page) throws BadRequestException {
        int validPage = getValidPage(page);
        PageRequest paging = PageRequest.of(validPage - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
        Page<BookmarkedSpeakingProb> bookmarkedSpeakingProbPage = bookmarkedSpeakingProbRepository.findAllByMember(member, paging);
        return bookmarkedSpeakingProbPage.map(BookmarkedSpeakingProbResponseDto::of);
    }

    private int getValidPage(int requestPage) {
        checkRequestPageValid(requestPage);
        int totalPage = calculateTotalPage();
        return (totalPage < requestPage) ? totalPage : requestPage;
    }

    private void checkRequestPageValid(int requestPage) {
        if(requestPage < 1) throw new BadRequestException(BadRequestErrorCode.SCALE_ERROR, "page는 1보다 작을 수 없습니다.");
    }

    private int calculateTotalPage() {
        Long bookmarkCount = bookmarkedSpeakingProbRepository.countByMember(member);
        int totalPage = (int) Math.ceil((bookmarkCount + 0.0) / PAGE_SIZE);
        return (totalPage < 1) ? 1 : totalPage;
    }

    public boolean isBookmarked(Long speakingProbId) {
        SpeakingProb speakingProb = getSpeakingProbOrElseThrow(speakingProbId);
        return bookmarkedSpeakingProbRepository.existsBySpeakingProbAndMember(speakingProb, member);
    }
}
