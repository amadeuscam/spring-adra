package com.amadeuscam.adratorrejon.repository;

import com.amadeuscam.adratorrejon.models.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, UUID> {
//    @Query("SELECT '*' FROM Beneficiario b WHERE b.id= ?1")
    public Optional<Beneficiario> findById(UUID id);
}
