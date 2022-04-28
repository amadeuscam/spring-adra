package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.dto.EmailValuesDTO;
import com.amadeuscam.adratorrejon.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping("/send")
    public ResponseEntity<?> sendEmail() {
        emailService.sendEmail();
        return new ResponseEntity("Correo enviado con exito!", HttpStatus.OK);
    }

    @PostMapping("/send-html")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        emailService.sendEmailTemplate(dto);
        return new ResponseEntity("Correo plantilla enviado con exito!", HttpStatus.OK);
    }
}
