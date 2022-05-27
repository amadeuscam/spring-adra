package com.amadeuscam.adratorrejon.repository;

import com.amadeuscam.adratorrejon.models.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FamiliarRepository extends JpaRepository<Familiar, Long> {

    public List<Familiar> findByBeneficiarioId(UUID beneficiario_id);
}
