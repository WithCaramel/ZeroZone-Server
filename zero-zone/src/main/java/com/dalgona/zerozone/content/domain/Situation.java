package com.dalgona.zerozone.content.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Situation implements Comparable<Situation> {

    @Id
    @Column(name = "SITUATION_ID")
    private Long id;

    @Column(length = 200, nullable = false, unique = true)
    private String situation;

    @Override
    public int compareTo(Situation s) {
        Long l = this.getId() - s.getId();
        return l.intValue();
    }

}
