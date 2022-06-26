package com.amadeuscam.adratorrejon.services;

import java.util.Map;
import java.util.UUID;

import com.amadeuscam.adratorrejon.dto.BeneficiarioDTO;
import com.amadeuscam.adratorrejon.dto.BeneficiarioResponse;

public interface BeneficiarioService {

    public BeneficiarioDTO createBeneficiario(BeneficiarioDTO beneficiarioDTO);

    public BeneficiarioResponse getAllBeneficiaios(int numberPage, int pageSize, String sortBy, String sortDir);
    public Map<String, Long> getAllBeneficiariosEstatisticas();

    public BeneficiarioDTO getBeneficiarioById(UUID id);

    public BeneficiarioDTO updateBeneficiario(BeneficiarioDTO beneficiarioDTO, UUID id);

    public void deleteBeneficiaioById(UUID id);

}
