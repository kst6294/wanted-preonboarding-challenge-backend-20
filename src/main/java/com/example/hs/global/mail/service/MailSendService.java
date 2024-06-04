package com.example.hs.global.mail.service;

import static com.example.hs.global.mail.MailConstant.EMAIL_AUTH;
import static com.example.hs.global.mail.MailConstant.EXPIRED_TIME_MENTION;
import static com.example.hs.global.mail.MailConstant.MAIL_DOMAIN;
import static com.example.hs.global.mail.MailConstant.MAIL_TEXT;
import static com.example.hs.global.mail.MailConstant.MAIL_TITLE_FOR_CERTIFIED;
import static com.example.hs.global.mail.MailConstant.OVER_TIME;
import static com.example.hs.global.mail.MailConstant.RESEND_COMPLETE;

import com.example.hs.global.mail.CertifiedNumberGenerator;
import com.example.hs.global.redis.auth.CertifiedNumberAuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendService {
  private final JavaMailSender javaMailSender;
  private final CertifiedNumberGenerator numberGenerator;
  private final CertifiedNumberAuthRepository certifiedNumberAuthRepository;

  public String emailAuthResend(String email) throws NoSuchAlgorithmException, MessagingException {
    if (certifiedNumberAuthRepository.hasKeyForEmailAuth(email)) {
      throw new RuntimeException("유효한 메일이 존재하므로 재전송은 생략합니다. 링크 유효시간이 지나면 다시 눌러주세요.");
    }

    String certificationNumber = numberGenerator.createCertificationNumber(6);
    String subject = MAIL_TITLE_FOR_CERTIFIED;
    String link = String.format(EMAIL_AUTH, MAIL_DOMAIN, email, certificationNumber);
    String expiredTimeMention = String.format(EXPIRED_TIME_MENTION, mailExpiredTime());
    String ifOverTime = String.format(OVER_TIME, MAIL_DOMAIN, email);
    String text = MAIL_TEXT + link + expiredTimeMention + ifOverTime;
    certifiedNumberAuthRepository.saveCertificationNumber(email, certificationNumber);

    sendMail(email, subject, text);

    return RESEND_COMPLETE;
  }
  public void emailAuth(String email) throws NoSuchAlgorithmException, MessagingException {
    String certificationNumber = numberGenerator.createCertificationNumber(6);
    String subject = MAIL_TITLE_FOR_CERTIFIED;
    String link = String.format(EMAIL_AUTH, MAIL_DOMAIN, email, certificationNumber);
    String expiredTimeMention = String.format(EXPIRED_TIME_MENTION, mailExpiredTime());
    String ifOverTime = String.format(OVER_TIME, MAIL_DOMAIN, email);
    String text = MAIL_TEXT + link + expiredTimeMention + ifOverTime;
    certifiedNumberAuthRepository.saveCertificationNumber(email, certificationNumber);

    sendMail(email, subject, text);
  }
  @Async
  public void sendMail(String to, String title, String content) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    helper.setTo(to);
    helper.setSubject(title);
    helper.setText(content, true);

    javaMailSender.send(message);
  }

  private String mailExpiredTime() {
    LocalDateTime currentTime = LocalDateTime.now();
    LocalDateTime expiredTime = currentTime.plusMinutes(10);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return expiredTime.format(formatter);
  }
}
