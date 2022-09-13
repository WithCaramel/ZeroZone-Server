package com.dalgona.zerozone.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OnlyResponseString {
    private String message;

    public static OnlyResponseString of(String message){
        return OnlyResponseString.builder()
                .message(message)
                .build();
    }
}

