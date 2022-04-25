package com.amadeuscam.adratorrejon.repository;

import com.amadeuscam.adratorrejon.models.Alimento;
import com.amadeuscam.adratorrejon.models.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    public List<Alimento> findByBeneficiarioId(long beneficiario_id);
}
