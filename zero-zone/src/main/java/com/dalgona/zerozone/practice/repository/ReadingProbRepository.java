package com.dalgona.zerozone.practice.repository;

import com.dalgona.zerozone.content.domain.Sentence;
import com.dalgona.zerozone.content.domain.Word;
import com.dalgona.zerozone.exam.dto.ReadingProbAndBookmarkJoin;
import com.dalgona.zerozone.practice.domain.ContentType;
import com.dalgona.zerozone.practice.domain.ReadingProb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReadingProbRepository extends JpaRepository<ReadingProb, Long> {
    Optional<ReadingProb> findByTypeAndWord(ContentType type, Word word);
    Optional<ReadingProb> findByTypeAndSentence(ContentType type, Sentence sentence);
    List<ReadingProb> findAllByType(ContentType type);

    @Query("select new com.dalgona.zerozone.exam.dto.ReadingProbAndBookmarkJoin(r.id, r.type, r.url, w.id, w.word, s.id, s.sentence, b.id)" +
            "from ReadingProb r left join BookmarkedReadingProb b on r = b.readingProb left join r.word w left join w.onset left join r.sentence s " +
            "where r.type = :type")
    List<ReadingProbAndBookmarkJoin> readingProbLeftJoinBookmarkByType(@Param("type") ContentType type);

    @Query("select new com.dalgona.zerozone.exam.dto.ReadingProbAndBookmarkJoin(r.id, r.type, r.url, w.id, w.word, s.id, s.sentence, b.id)" +
            "from ReadingProb r left join BookmarkedReadingProb b on r = b.readingProb left join r.word w left join w.onset left join r.sentence s")
    List<ReadingProbAndBookmarkJoin> readingProbLeftJoinBookmark();

    Optional<ReadingProb> findBySentence(Sentence sentence);
    Optional<ReadingProb> findByWord(Word word);

    int countByType(ContentType word);

}

