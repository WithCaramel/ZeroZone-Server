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
public class Nucleus {

    @Id
    @Column(name = "NUCLEUS_ID")
    private Long id;

    @Column(length = 5, nullable = false, unique = true)
    private String nucleus;

}
