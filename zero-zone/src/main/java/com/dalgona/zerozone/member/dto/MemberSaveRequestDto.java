package com.dalgona.zerozone.member.dto;

import com.dalgona.zerozone.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveRequestDto {
    @NotNull(message = "email은 null일 수 없습니다.")
    @Email(message = "email 형식을 지켜야합니다.")
    String email;

    @NotNull(message = "password는 null일 수 없습니다.")
    @NotEmpty(message = "회원의 password는 빈값일 수 없습니다.")
    String password;

    @NotNull(message = "회원의 name은 null일 수 없습니다.")
    @NotEmpty(message = "회원의 name은 빈값일 수 없습니다.")
    String name;

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

}
