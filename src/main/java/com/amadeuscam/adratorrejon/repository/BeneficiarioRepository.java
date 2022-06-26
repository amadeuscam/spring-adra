package com.amadeuscam.adratorrejon.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amadeuscam.adratorrejon.models.Beneficiario;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, UUID> {
//    @Query("SELECT '*' FROM Beneficiario b WHERE b.id= ?1")
    public Optional<Beneficiario> findById(UUID id);
    List<Beneficiario> findAllByfechanacimiento(Integer age);
}
