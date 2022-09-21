package com.dalgona.zerozone.bookmark.service;

import com.dalgona.zerozone.bookmark.domain.BookmarkedReadingProb;
import com.dalgona.zerozone.bookmark.dto.BookmarkedReadingProbResponseDto;
import com.dalgona.zerozone.bookmark.repository.BookmarkedReadingProbRepository;
import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import com.dalgona.zerozone.practice.repository.ReadingProbRepository;
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
public class BookmarkReadingService implements BookmarkServiceInterface{

    private final ReadingProbRepository readingProbRepository;
    private final BookmarkedReadingProbRepository bookmarkedReadingProbRepository;
    @Setter private Member member;
    final int PAGE_SIZE = 50;

    @Transactional
    @Override
    public void addBookmark(Long readingProbId) {
        ReadingProb readingProb = getReadingProbOrElseThrow(readingProbId);
        BookmarkedReadingProb bookmarkedReadingProb = getBookmarkedReadingProbToSave(readingProb);
        bookmarkedReadingProbRepository.save(bookmarkedReadingProb);
    }

    private BookmarkedReadingProb getBookmarkedReadingProbToSave(ReadingProb readingProb) {
        Optional<BookmarkedReadingProb> optionalBookmarkedReadingProb = bookmarkedReadingProbRepository.findByMemberAndReadingProb(member, readingProb);
        if(optionalBookmarkedReadingProb.isPresent()) throw new BadRequestException(BadRequestErrorCode.DUPLICATED, "이미 북마크로 등록되었습니다.");
        return createBookmarkedReadingProb(readingProb);
    }

    private BookmarkedReadingProb createBookmarkedReadingProb(ReadingProb readingProb) {
        BookmarkedReadingProb bookmarkedReadingProb = BookmarkedReadingProb.builder()
                .readingProb(readingProb)
                .member(member)
                .build();
        return bookmarkedReadingProb;
    }

    private ReadingProb getReadingProbOrElseThrow(Long readingProbId) {
        ReadingProb readingProb = readingProbRepository.findById(readingProbId)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "존재하지 않는 연습 구화 문제입니다."));
        return readingProb;
    }

    @Transactional
    @Override
    public void unBookmark(Long readingProbId) {
        ReadingProb readingProb = getReadingProbOrElseThrow(readingProbId);
        checkReadingProbBookmarked(readingProb);
        bookmarkedReadingProbRepository.deleteByReadingProbAndMember(readingProb, member);
    }

    private void checkReadingProbBookmarked(ReadingProb readingProb) {
        if(!bookmarkedReadingProbRepository.existsByReadingProbAndMember(readingProb, member))
            throw new BadRequestException(BadRequestErrorCode.NOT_FOUND, "북마크로 등록되지 않은 구화 연습 문제입니다.");
    }

    @Transactional
    @Override
    public Page<BookmarkedReadingProbResponseDto> getBookmarkListWithPage(int page) throws BadRequestException {
        int validPage = getValidPage(page);
        PageRequest paging = PageRequest.of(validPage - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
        Page<BookmarkedReadingProb> bookmarkedReadingProbPage = bookmarkedReadingProbRepository.findAllByMember(member, paging);
        return bookmarkedReadingProbPage.map(BookmarkedReadingProbResponseDto::of);
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
        Long bookmarkCount = bookmarkedReadingProbRepository.countByMember(member);
        int totalPage = (int) Math.ceil((bookmarkCount + 0.0) / PAGE_SIZE);
        return (totalPage < 1) ? 1 : totalPage;
    }

    public boolean isBookmarked(Long readingProbId) {
        ReadingProb readingProb = getReadingProbOrElseThrow(readingProbId);
        return bookmarkedReadingProbRepository.existsByReadingProbAndMember(readingProb, member);
    }
}
