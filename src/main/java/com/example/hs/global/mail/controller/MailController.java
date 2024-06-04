package com.example.hs.global.mail.controller;


import com.example.hs.domain.auth.dto.MemberDto;
import com.example.hs.global.mail.service.MailSendService;
import com.example.hs.global.mail.service.MailVerifyService;
import jakarta.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mails")
public class MailController {
  private final MailVerifyService mailVerifyService;
  private final MailSendService mailSendService;

  @GetMapping("/check/{email}/{authNumber}")
  public ResponseEntity<MemberDto> verifyMail(@PathVariable String email, @PathVariable String authNumber) {
    return ResponseEntity.ok(mailVerifyService.verifyEmail(email, authNumber));
  }

  @GetMapping("/re-send/{email}")
  public ResponseEntity<String> resendMail(@PathVariable String email)
      throws MessagingException, NoSuchAlgorithmException {
    return ResponseEntity.ok(mailSendService.emailAuthResend(email));
  }
}
