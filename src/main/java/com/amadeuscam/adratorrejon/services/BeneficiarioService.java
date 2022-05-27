package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.BeneficiarioDTO;
import com.amadeuscam.adratorrejon.dto.BeneficiarioResponse;

import java.util.UUID;

public interface BeneficiarioService {

    public BeneficiarioDTO createBeneficiario(BeneficiarioDTO beneficiarioDTO);

    public BeneficiarioResponse getAllBeneficiaios(int numberPage, int pageSize, String sortBy, String sortDir);

    public BeneficiarioDTO getBeneficiarioById(UUID id);

    public BeneficiarioDTO updateBeneficiario(BeneficiarioDTO beneficiarioDTO, UUID id);

    public void deleteBeneficiaioById(UUID id);

}
