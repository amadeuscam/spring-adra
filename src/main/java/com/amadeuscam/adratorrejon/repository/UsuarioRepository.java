package com.amadeuscam.adratorrejon.repository;


import com.amadeuscam.adratorrejon.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmail(String email);

    public Optional<Usuario> findByUsername(String username);

    public Optional<Usuario> findByTokenPassword(String tokenPassword);

    public Optional<Usuario> findByEmailOrUsername(String email, String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);

}
