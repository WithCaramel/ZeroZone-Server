package com.dalgona.zerozone.bookmark.service;

import com.dalgona.zerozone.member.domain.Member;

public interface BookmarkServiceInterface {
    public void addBookmark(Long probId);
    public void unBookmark(Long probId);
    public Object getBookmarkListWithPage(int page);
}
