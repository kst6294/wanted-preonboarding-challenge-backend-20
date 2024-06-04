package com.example.hs.global.mail.service;

import static com.example.hs.global.mail.MailConstant.EMAIL_AUTH;
import static com.example.hs.global.mail.MailConstant.EXPIRED_TIME_MENTION;
import static com.example.hs.global.mail.MailConstant.MAIL_DOMAIN;
import static com.example.hs.global.mail.MailConstant.MAIL_TEXT;
import static com.example.hs.global.mail.MailConstant.MAIL_TITLE_FOR_CERTIFIED;

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

  public void emailAuth(String email) throws NoSuchAlgorithmException, MessagingException {
    String certificationNumber = numberGenerator.createCertificationNumber(6);
    String subject = MAIL_TITLE_FOR_CERTIFIED;
    String link = String.format(EMAIL_AUTH, MAIL_DOMAIN, email, certificationNumber);
    String expiredTimeMention = String.format(EXPIRED_TIME_MENTION, mailExpiredTime());
    String text = MAIL_TEXT + link + expiredTimeMention;
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
