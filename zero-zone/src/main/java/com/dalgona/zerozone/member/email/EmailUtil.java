package com.dalgona.zerozone.member.email;

import com.dalgona.zerozone.common.exception.InternalServerErrorCode;
import com.dalgona.zerozone.common.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;

@Component
@EnableAsync
@RequiredArgsConstructor
public class EmailUtil {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.password}")
    private String fromEmailpassword;

    private final JavaMailSender emailSender;

    @Async
    @Transactional
    public void sendHtmlEmail(String subject, String content, String toEmail){
        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject(subject);
            message.setContent(content,"text/html;charset=euc-kr");
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerException(InternalServerErrorCode.EMAIL_SEND_FAIL);
        }
    }

}
