package com.amadeuscam.adratorrejon.repository;

import com.amadeuscam.adratorrejon.models.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamiliarRepository extends JpaRepository<Familiar, Long> {

    public List<Familiar> findByBeneficiarioId(long beneficiario_id);
}
