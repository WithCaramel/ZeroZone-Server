package com.dalgona.zerozone.bookmark;

import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookmarkedReadingProb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKMARKREADINGPROB_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne
    @JoinColumn(name = "READINGPROB_ID")
    private ReadingProb readingProb;

    @Builder
    public BookmarkedReadingProb(Member member, ReadingProb readingProb){
        this.member = member;
        this.readingProb = readingProb;
    }

    @Override
    public boolean equals(Object object) {
        BookmarkedReadingProb findBookmarkedReadingProb = (BookmarkedReadingProb) object;
        ReadingProb findReadingProb = findBookmarkedReadingProb.getReadingProb();
        if (findReadingProb.getId() == this.readingProb.getId() &&
                (findReadingProb.getType().compareTo(this.readingProb.getType())==0)) {
            return true;
        }
        return false;
    }
}
