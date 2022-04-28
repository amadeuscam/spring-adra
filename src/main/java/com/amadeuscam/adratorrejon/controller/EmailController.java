package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.dto.ChangePasswordDTO;
import com.amadeuscam.adratorrejon.dto.EmailValuesDTO;
import com.amadeuscam.adratorrejon.models.Usuario;
import com.amadeuscam.adratorrejon.services.EmailService;
import com.amadeuscam.adratorrejon.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    EmailService emailService;
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @GetMapping("/send")
    public ResponseEntity<?> sendEmail() {
        emailService.sendEmail();
        return new ResponseEntity("Correo enviado con exito!", HttpStatus.OK);
    }

    @PostMapping("/send-html")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        Optional<Usuario> usuarioOptional = usuarioService.findByEmailOrUsername(dto.getMailTo());
        if (!usuarioOptional.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("response", "No existe ningun usuarioo con estas credenciales"), HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioOptional.get();
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        dto.setMailFrom(mailFrom);
        dto.setMailTo(usuario.getEmail());
        dto.setSubject("Cambio de Contraseña");
        dto.setUserName(usuario.getUsername());
        dto.setJwt(tokenPassword);
        usuario.setTokenPassword(tokenPassword);
        usuarioService.save(usuario);
        emailService.sendEmailTemplate(dto);
        return new ResponseEntity("Correo plantilla enviado con exito!", HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(Collections.singletonMap("response", "campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword()))
            return new ResponseEntity(Collections.singletonMap("response", "Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuarioOptional = usuarioService.findByTokenPassword(dto.getTokenPassword());
        if (!usuarioOptional.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("response", "No existe ningun usuario con estas credenciales"), HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioOptional.get();
        String newPassword = passwordEncoder.encode(dto.getPassword());
        usuario.setPassword(newPassword);
        usuario.setTokenPassword(null);
        usuarioService.save(usuario);

        return new ResponseEntity<>(Collections.singletonMap("response", "Contraseña cambiada con exito!"), HttpStatus.OK);

    }
}
