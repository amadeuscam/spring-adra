package com.amadeuscam.adratorrejon.repository;


import com.amadeuscam.adratorrejon.enums.RolName;
import com.amadeuscam.adratorrejon.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    public Optional<Rol> findByRolName(RolName rolName);
}
