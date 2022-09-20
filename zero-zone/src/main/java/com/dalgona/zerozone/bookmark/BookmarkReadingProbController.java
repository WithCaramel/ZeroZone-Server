package com.dalgona.zerozone.bookmark;

import com.dalgona.zerozone.common.response.OnlyResponseString;
import com.dalgona.zerozone.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/reading-practices/bookmark")
public class BookmarkReadingProbController {
    public final BookmarkReadingService bookmarkReadingService;

    @PostMapping("/{readingProbId}")
    public OnlyResponseString addBookmarkedReadingProb(
            @PathVariable Long readingProbId,
            @AuthenticationPrincipal Member member
    ){
        bookmarkReadingService.setMember(member);
        bookmarkReadingService.addBookmark(readingProbId);
        return OnlyResponseString.of("북마크 추가에 성공했습니다.");
    }

    @DeleteMapping("/{readingProbId}")
    public OnlyResponseString deleteBookmarkedReadingProb(
            @PathVariable Long readingProbId,
            @AuthenticationPrincipal Member member
    ){
        bookmarkReadingService.setMember(member);
        bookmarkReadingService.unBookmark(readingProbId);
        return OnlyResponseString.of("북마크 삭제에 성공했습니다.");
    }

    @GetMapping
    public Page<BookmarkedReadingProbResponseDto> getBookmarkedReadingProb(
            @AuthenticationPrincipal Member member,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page
    ){
        bookmarkReadingService.setMember(member);
        return bookmarkReadingService.getBookmarkListWithPage(page);
    }

}
