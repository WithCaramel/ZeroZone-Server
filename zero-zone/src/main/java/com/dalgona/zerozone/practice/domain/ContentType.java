package com.dalgona.zerozone.practice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ContentType {
    LETTER("글자"),
    WORD("단어"),
    SENTENCE("문장");

    private String description;
}
