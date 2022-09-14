package com.dalgona.zerozone.member.service;

import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.common.exception.InternalServerException;
import com.dalgona.zerozone.member.domain.EmailAuth;
import com.dalgona.zerozone.member.domain.EmailAuthRepository;
import com.dalgona.zerozone.member.email.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailAuthService {
    private final EmailUtil emailUtil;
    private final EmailAuthRepository emailAuthRepository;

    @Transactional
    public void requestEmailVerify(String email){
        String authCode = EmailAuth.createCode();
        if(emailAuthRepository.existsByEmail(email)) updateEmailAuth(authCode, email);
        else saveEmailAuth(authCode, email);
        sendAuthCodeEmail(authCode, email);
    }

    private void updateEmailAuth(String authCode, String email) {
        EmailAuth emailAuth = getEmailAuthOrElseThrow(email);
        emailAuth.updateAuthed(false);
        emailAuth.updateAuthCode(authCode);
        emailAuth.updateAuthValidTime(LocalDateTime.now().plusMinutes(5));
        emailAuthRepository.save(emailAuth);
    }

    private void saveEmailAuth(String authCode, String email){
        EmailAuth emailAuth = EmailAuth.builder()
                .authCode(authCode)
                .email(email)
                .authValidTime(LocalDateTime.now().plusMinutes(5))
                .authed(false)
                .build();
        emailAuthRepository.save(emailAuth);
    }

    private void sendAuthCodeEmail(String authCode, String toEmail) throws InternalServerException {
        String subject = "ZeroZone 회원가입 이메일 인증";
        String content = createAuthCodeEmailContent(authCode);
        emailUtil.sendHtmlEmail(subject, content, toEmail);
    }

    private String createAuthCodeEmailContent(String authCode) {
        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 ZeroZone 이메일 인증 칸에 입력하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += authCode;
        msg += "</td></tr></tbody></table></div>";
        return msg;
    }

    @Transactional
    public void verifyEmail(String email, String authCode) throws BadRequestException {
        EmailAuth emailAuth = getEmailAuthOrElseThrow(email);
        checkEmailAuthCodeValid(authCode, emailAuth);
        emailAuth.updateAuthed(true);
    }

    private EmailAuth getEmailAuthOrElseThrow(String requestedEmail) throws BadRequestException {
        return emailAuthRepository.findByEmail(requestedEmail)
                .orElseThrow(() -> new BadRequestException(BadRequestErrorCode.NOT_FOUND, "이메일 인증을 요청한 적 없는 회원입니다."));
    }

    public static void checkEmailAuthCodeValid(String authCode, EmailAuth emailAuth) throws BadRequestException {
        if(emailAuth.isEmailAuthCodeWrong(authCode)) throw new BadRequestException(BadRequestErrorCode.NOT_MATCHES, "인증 코드가 일치하지 않습니다.");
        if(emailAuth.isEmailAuthCodeExpired()) throw new BadRequestException(BadRequestErrorCode.TIME_OUT, "인증코드 유효 시간이 지났습니다.");
    }


}
