package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.FamiliarDTO;

import java.util.List;

public interface FamiliarService {

    public FamiliarDTO createFamiliar(long beneficiario_id, FamiliarDTO familiarDTO);

    public List<FamiliarDTO> getAllFamiliaresPerBeneficarioId(long beneficiario_id);

    public FamiliarDTO getFamiliarById(long beneficiario_id, long familiar_id);

    public void deleteFamiliar(long beneficiario_id, long familiar_id);

    public FamiliarDTO updateFamiliar(FamiliarDTO familiarDTO, Long beneficiario_id, Long familiar_id);
}
