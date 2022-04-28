package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.models.Usuario;
import com.amadeuscam.adratorrejon.models.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByEmailOrUsername(usernameOrEmail).
                orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con este username o email" + usernameOrEmail));;
//        Usuario user = userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
        return UsuarioPrincipal.build(usuario);
    }
}
