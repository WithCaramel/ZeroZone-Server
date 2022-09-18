package com.dalgona.zerozone.content.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Onset implements Comparable<Onset> {
    @Id
    @Column(name = "ONSET_ID")
    private Long id;

    @Column(length = 5, nullable = false, unique = true)
    private String onset;

    @Override
    public int compareTo(Onset o) {
        Long l = this.getId() - o.getId();
        return l.intValue();
    }
}
