package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.UsuarioDTO;
import com.amadeuscam.adratorrejon.models.Usuario;

import java.util.HashMap;
import java.util.Optional;

public interface UsuarioService {

    public UsuarioDTO getUserInfo(String usernameoremail);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);

    public Optional<Usuario> getByUserName(String nombreUsuario);

    public Optional<Usuario> findByEmailOrUsername(String nameOrEmail);

    public void save(Usuario usuario);

}
