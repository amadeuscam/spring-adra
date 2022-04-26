package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.dto.FamiliarDTO;
import com.amadeuscam.adratorrejon.dto.LoginDTO;
import com.amadeuscam.adratorrejon.dto.RegistryDTO;
import com.amadeuscam.adratorrejon.dto.UsuarioDTO;
import com.amadeuscam.adratorrejon.enums.RolName;
import com.amadeuscam.adratorrejon.exceptions.AdraAppException;
import com.amadeuscam.adratorrejon.models.Rol;
import com.amadeuscam.adratorrejon.models.Usuario;
import com.amadeuscam.adratorrejon.repository.RolRepository;
import com.amadeuscam.adratorrejon.repository.UsuarioRepository;
import com.amadeuscam.adratorrejon.security.JWTAuthResponseDTO;
import com.amadeuscam.adratorrejon.security.JwtTokenProvider;
import com.amadeuscam.adratorrejon.services.RolService;
import com.amadeuscam.adratorrejon.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @PostMapping("/user")
    public ResponseEntity<UsuarioDTO> getUser(@RequestBody String username) {
        System.out.println(username);
        UsuarioDTO usuarioDTO = usuarioService.getUserInfo(username);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authUser(@RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(Collections.singletonMap("response", "campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.generateToken(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> NewUser(@Valid @RequestBody RegistryDTO registryDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(Collections.singletonMap("response", "campos mal puestos o email invalido!"), HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.existsByUsername(registryDTO.getUsername())) {
            return new ResponseEntity<>(Collections.singletonMap("response", "El nombre de usuario existe!"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByEmail(registryDTO.getEmail())) {
            return new ResponseEntity<>(Collections.singletonMap("response", "El email de usuario existe!"), HttpStatus.BAD_REQUEST);
        }
        System.out.println(registryDTO.toString());
        Usuario usuario = new Usuario(
                registryDTO.getName(),
                registryDTO.getUsername(),
                registryDTO.getEmail(),
                passwordEncoder.encode(registryDTO.getPassword())
        );
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getRoleName(RolName.ROLE_USER).get());
        if (registryDTO.getRoles().contains("admin")) {
            roles.add(rolService.getRoleName(RolName.ROLE_ADMIN).get());
        }
        usuario.setRoles(roles);
        usuarioService.save(usuario);


        return new ResponseEntity<>(Collections.singletonMap("response", "usuario creado"), HttpStatus.CREATED);

    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthResponseDTO> refresh(@RequestBody JWTAuthResponseDTO jwtDto) throws ParseException {
        String token = jwtTokenProvider.refreshToken(jwtDto);
        JWTAuthResponseDTO jwt = new JWTAuthResponseDTO(token);
        return new ResponseEntity(jwt, HttpStatus.OK);
    }

}
