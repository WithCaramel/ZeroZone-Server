package com.dalgona.zerozone.bookmark.controller;

import com.dalgona.zerozone.bookmark.dto.BookmarkedSpeakingProbResponseDto;
import com.dalgona.zerozone.bookmark.service.BookmarkSpeakingService;
import com.dalgona.zerozone.common.response.OnlyResponseString;
import com.dalgona.zerozone.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/speaking-practices/bookmark")
public class BookmarkSpeakingProbController {
    public final BookmarkSpeakingService bookmarkSpeakingService;

    @PostMapping("/{speakingProbId}")
    public OnlyResponseString addBookmarkedSpeakingProb(
            @PathVariable Long speakingProbId,
            @AuthenticationPrincipal Member member
    ){
        bookmarkSpeakingService.setMember(member);
        bookmarkSpeakingService.addBookmark(speakingProbId);
        return OnlyResponseString.of("북마크 추가에 성공했습니다.");
    }

    @DeleteMapping("/{speakingProbId}")
    public OnlyResponseString deleteBookmarkedSpeakingProb(
            @PathVariable Long speakingProbId,
            @AuthenticationPrincipal Member member
    ){
        bookmarkSpeakingService.setMember(member);
        bookmarkSpeakingService.unBookmark(speakingProbId);
        return OnlyResponseString.of("북마크 삭제에 성공했습니다.");
    }

    @GetMapping
    public Page<BookmarkedSpeakingProbResponseDto> getBookmarkedSpeakingProb(
            @AuthenticationPrincipal Member member,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page
    ){
        bookmarkSpeakingService.setMember(member);
        return bookmarkSpeakingService.getBookmarkListWithPage(page);
    }
}
