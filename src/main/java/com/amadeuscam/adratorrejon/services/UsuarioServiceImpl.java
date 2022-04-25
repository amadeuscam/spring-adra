package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.FamiliarDTO;
import com.amadeuscam.adratorrejon.dto.UsuarioDTO;
import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Familiar;
import com.amadeuscam.adratorrejon.models.Usuario;
import com.amadeuscam.adratorrejon.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO getUserInfo(String usernameoremail) {
        Usuario usuario = usuarioRepository.findByEmailOrUsername(usernameoremail, usernameoremail).
                orElseThrow(() -> new ResourceNotFound("Usuario", "usernameoremail", 1));

        return mapearDTO(usuario);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Optional<Usuario> getByUserName(String nombreUsuario) {
        return usuarioRepository.findByUsername(nombreUsuario);
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO mapearDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        return usuarioDTO;
    }


}
