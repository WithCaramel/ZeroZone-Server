package com.dalgona.zerozone.member.dto;

import com.dalgona.zerozone.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveRequestDto {
    @Email String email;
    @NotEmpty(message = "회원의 password는 빈값일 수 없습니다.") String password;
    @NotEmpty(message = "회원의 name은 빈값일 수 없습니다.") String name;

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

}
