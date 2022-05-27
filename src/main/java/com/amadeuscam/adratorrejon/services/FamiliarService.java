package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.FamiliarDTO;

import java.util.List;
import java.util.UUID;

public interface FamiliarService {

    public FamiliarDTO createFamiliar(UUID beneficiario_id, FamiliarDTO familiarDTO);

    public List<FamiliarDTO> getAllFamiliaresPerBeneficarioId(UUID beneficiario_id);

    public FamiliarDTO getFamiliarById(UUID beneficiario_id, long familiar_id);

    public void deleteFamiliar(UUID beneficiario_id, long familiar_id);

    public FamiliarDTO updateFamiliar(FamiliarDTO familiarDTO, UUID beneficiario_id, Long familiar_id);
}
