package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.FamiliarDTO;
import com.amadeuscam.adratorrejon.exceptions.AdraAppException;
import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Beneficiario;
import com.amadeuscam.adratorrejon.models.Familiar;
import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;
import com.amadeuscam.adratorrejon.repository.FamiliarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FamiliarServiceImpl implements FamiliarService {

    @Autowired
    public FamiliarRepository familiarRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BeneficiarioRepository beneficiarioRepository;

    /**
     * @param beneficiario_id
     * @param familiarDTO
     * @return la version json del familiar
     */
    @Override
    public FamiliarDTO createFamiliar(UUID beneficiario_id, FamiliarDTO familiarDTO) {
        Familiar familiar = mapearEntity(familiarDTO);
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id).
                orElseThrow(() -> new ResourceNotFound("Publicacion", "id", beneficiario_id.toString()));
        familiar.setBeneficiario(beneficiario);
        Familiar newFamiliar = familiarRepository.save(familiar);
        return mapearDTO(newFamiliar);
    }

    /**
     * @param beneficiario_id
     * @return todos los familiares asociados al beneficiario
     */
    @Override
    public List<FamiliarDTO> getAllFamiliaresPerBeneficarioId(UUID beneficiario_id) {
        List<Familiar> familiars = familiarRepository.findByBeneficiarioId(beneficiario_id);
        return familiars.stream().map(familiar -> mapearDTO(familiar)).collect(Collectors.toList());
    }

    /**
     * @param beneficiario_id
     * @param familiar_id
     * @return
     */
    @Override
    public FamiliarDTO getFamiliarById(UUID beneficiario_id, long familiar_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id).
                orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id.toString()));

        Familiar familiar = familiarRepository.findById(familiar_id).
                orElseThrow(() -> new ResourceNotFound("Familiar", "id", beneficiario_id.toString()));

        if (!familiar.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El familiar no tiene beneficiario");
        }
        return mapearDTO(familiar);
    }

    /**
     * @param beneficiario_id
     * @param familiar_id
     */
    @Override
    public void deleteFamiliar(UUID beneficiario_id, long familiar_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id).
                orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id.toString()));

        Familiar familiar = familiarRepository.findById(familiar_id).
                orElseThrow(() -> new ResourceNotFound("Familiar", "id", beneficiario_id.toString()));

        if (!familiar.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El familiar no tiene beneficiario");
        }
        familiarRepository.delete(familiar);
    }

    /**
     * @param familiarDTO
     * @param beneficiario_id
     * @param familiar_id
     * @return
     */
    @Override
    public FamiliarDTO updateFamiliar(FamiliarDTO familiarDTO, UUID beneficiario_id, Long familiar_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id).
                orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id.toString()));

        Familiar familiar = familiarRepository.findById(familiar_id).
                orElseThrow(() -> new ResourceNotFound("Familiar", "id", beneficiario_id.toString()));

        if (!familiar.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El familiar no tiene beneficiario");
        }
        familiar.setActive(familiarDTO.isActive());
        familiar.setDni(familiarDTO.getDni());
        familiar.setEdad(familiarDTO.getEdad());
        familiar.setNombreapellido(familiarDTO.getNombreapellido());
        familiar.setParentesco(familiarDTO.getParentesco());
        familiar.setSexo(familiarDTO.getSexo());
        familiar.setOtros_documentos(familiarDTO.getOtros_documentos());
        familiar.setFechanacimiento(familiarDTO.getFechanacimiento());

        Familiar familiarUpdate = familiarRepository.save(familiar);
        return mapearDTO(familiarUpdate);
    }

    private FamiliarDTO mapearDTO(Familiar familiar) {
        FamiliarDTO familiarDTO = modelMapper.map(familiar, FamiliarDTO.class);
        return familiarDTO;
    }

    private Familiar mapearEntity(FamiliarDTO familiarDTO) {
        Familiar familiar = modelMapper.map(familiarDTO, Familiar.class);
        return familiar;
    }
}
