package com.dalgona.zerozone.security.dto;

import com.dalgona.zerozone.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoResponseDto {
    private String email;
    private String name;

    public static MemberInfoResponseDto of(Member member){
        return MemberInfoResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
