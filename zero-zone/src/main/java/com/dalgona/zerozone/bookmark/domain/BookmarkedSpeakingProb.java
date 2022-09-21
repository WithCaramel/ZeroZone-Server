package com.dalgona.zerozone.bookmark.domain;

import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.SpeakingProb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookmarkedSpeakingProb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKMARKSPEAKINGPROB_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne
    @JoinColumn(name = "SPEAKINGPROB_ID")
    private SpeakingProb speakingProb;

    @Builder
    public BookmarkedSpeakingProb(Member member, SpeakingProb speakingProb){
        this.member = member;
        this.speakingProb = speakingProb;
    }

    @Override
    public boolean equals(Object object) {
        BookmarkedSpeakingProb findBookmarkedSpeakingProb = (BookmarkedSpeakingProb) object;
        SpeakingProb findSpeakingProb = findBookmarkedSpeakingProb.getSpeakingProb();
        if (findSpeakingProb.getId() == this.speakingProb.getId() &&
                (findSpeakingProb.getType().compareTo(this.speakingProb.getType())==0)) {
            return true;
        }
        return false;
    }
}
