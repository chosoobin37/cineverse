package com.cineverse.cineversebackend.user.email.controller;

import com.cineverse.cineversebackend.user.email.dto.EmailMessage;
import com.cineverse.cineversebackend.user.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

//    @PostMapping("/send")
//    public ResponseEntity sendMail() {
//        EmailMessage emailMessage = EmailMessage.builder()
//                .to("yunjaeeun12@naver.com")
//                .subject("테스트메일")
//                .message("비밀번호 재설정을 위한 이메일")
//                .build();
//        emailService.sendMail(emailMessage);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @PostMapping("/send")
    public ResponseEntity<EmailMessage> sendMail(@RequestBody EmailMessage emailMessage) {
        emailService.sendMail(emailMessage);
        return new ResponseEntity(HttpStatus.OK);
    }
}
