package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.AlimentoDTO;
import com.amadeuscam.adratorrejon.dto.FamiliarDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface AlimentoService {
    public AlimentoDTO createAlimento(UUID beneficiario_id, AlimentoDTO alimentoDTO);

    public List<AlimentoDTO> getAllAlimentosPerBeneficarioId(UUID beneficiario_id);

    public AlimentoDTO getAlimentosById(UUID beneficiario_id, long al_id);

    public void deleteAlimentos(UUID beneficiario_id, long al_id);

    public AlimentoDTO updateAlimentos(AlimentoDTO alimentoDTO, UUID beneficiario_id, Long al_id);
}
