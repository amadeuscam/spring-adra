package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.AlimentoDTO;
import com.amadeuscam.adratorrejon.dto.FamiliarDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AlimentoService {
    public AlimentoDTO createAlimento(long beneficiario_id, AlimentoDTO alimentoDTO);

    public List<AlimentoDTO> getAllAlimentosPerBeneficarioId(long beneficiario_id);

    public AlimentoDTO getAlimentosById(long beneficiario_id, long al_id);

    public void deleteAlimentos(long beneficiario_id, long al_id);

    public AlimentoDTO updateAlimentos(AlimentoDTO alimentoDTO, Long beneficiario_id, Long al_id);
}
