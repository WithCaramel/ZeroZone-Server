package com.dalgona.zerozone.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmailAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column
    private String authCode;

    @Column
    private LocalDateTime authValidTime;

    @Column
    private Boolean authed;

    @Builder
    public EmailAuth(String email, String authCode, LocalDateTime authValidTime, Boolean authed){
        this.email = email;
        this.authCode = authCode;
        this.authValidTime = authValidTime;
        this.authed = authed;
    }

    public EmailAuth updateAuthed(Boolean authed){
        this.authed = authed;
        return this;
    }

    public EmailAuth updateAuthCode(String authCode){
        this.authCode = authCode;
        return this;
    }

    public EmailAuth updateAuthValidTime(LocalDateTime authValidTime){
        this.authValidTime = authValidTime;
        return this;
    }

    public static String createCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    public boolean isEmailAuthCodeWrong(String requestedAuthCode){
        return authCode.compareTo(requestedAuthCode) != 0;
    }

    public boolean isEmailAuthCodeExpired(){
        return authValidTime.isBefore(LocalDateTime.now());
    }

}

