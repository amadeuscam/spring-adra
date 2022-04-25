package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.BeneficiarioDTO;
import com.amadeuscam.adratorrejon.dto.BeneficiarioResponse;

public interface BeneficiarioService {

    public BeneficiarioDTO createBeneficiario(BeneficiarioDTO beneficiarioDTO);

    public BeneficiarioResponse getAllBeneficiaios(int numberPage, int pageSize, String sortBy, String sortDir);

    public BeneficiarioDTO getBeneficiarioById(long id);

    public BeneficiarioDTO updateBeneficiario(BeneficiarioDTO beneficiarioDTO, long id);

    public void deleteBeneficiaioById(long id);

}
