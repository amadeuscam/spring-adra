package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.AlimentoDTO;
import com.amadeuscam.adratorrejon.exceptions.AdraAppException;
import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Alimento;
import com.amadeuscam.adratorrejon.models.Beneficiario;
import com.amadeuscam.adratorrejon.repository.AlimentoRepository;
import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlimentoImpl implements AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BeneficiarioRepository beneficiarioRepository;


    @Override
    public AlimentoDTO createAlimento(long beneficiario_id, AlimentoDTO alimentoDTO) {
        Alimento alimento = mapearEntity(alimentoDTO);
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id).orElseThrow(() -> new ResourceNotFound("Publicacion", "id", beneficiario_id));
        alimento.setBeneficiario(beneficiario);
        Alimento newAlimentos = alimentoRepository.save(alimento);
        return mapearDTO(newAlimentos);
    }

    @Override
    public List<AlimentoDTO> getAllAlimentosPerBeneficarioId(long beneficiario_id) {
        List<Alimento> alimentos = alimentoRepository.findByBeneficiarioId(beneficiario_id);
        return alimentos.stream().map(alimento -> mapearDTO(alimento)).collect(Collectors.toList());
    }

    @Override
    public AlimentoDTO getAlimentosById(long beneficiario_id, long al_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id).orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id));

        Alimento alimento = alimentoRepository.findById(al_id).orElseThrow(() -> new ResourceNotFound("Alimento", "id", al_id));

        if (!alimento.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El Alimento no tiene beneficiario");
        }
        return mapearDTO(alimento);
    }

    @Override
    public void deleteAlimentos(long beneficiario_id, long al_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id).orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id));

        Alimento alimento = alimentoRepository.findById(al_id).orElseThrow(() -> new ResourceNotFound("Alimento", "id", al_id));

        if (!alimento.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El Alimento no tiene beneficiario");
        }
        alimentoRepository.delete(alimento);
    }

    @Override
    public AlimentoDTO updateAlimentos(AlimentoDTO alimentoDTO, Long beneficiario_id, Long al_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id).orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id));

        Alimento alimento = alimentoRepository.findById(al_id).orElseThrow(() -> new ResourceNotFound("Alimento", "id", al_id));

        if (!alimento.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El Alimento no tiene beneficiario");
        }
        alimento.setAlimento1(alimentoDTO.getAlimento1());
        alimento.setAlimento2(alimentoDTO.getAlimento2());
        alimento.setAlimento3(alimentoDTO.getAlimento3());
        alimento.setAlimento4(alimentoDTO.getAlimento4());
        alimento.setAlimento5(alimentoDTO.getAlimento5());
        alimento.setAlimento6(alimentoDTO.getAlimento6());
        alimento.setAlimento7(alimentoDTO.getAlimento7());
        alimento.setAlimento8(alimentoDTO.getAlimento8());
        alimento.setAlimento9(alimentoDTO.getAlimento9());
        alimento.setAlimento10(alimentoDTO.getAlimento10());
        alimento.setAlimento11(alimentoDTO.getAlimento11());
        alimento.setAlimento12(alimentoDTO.getAlimento12());
        alimento.setUpdatedDate(Instant.now());
        alimento.setCreatedDate(Instant.now());
        Alimento alimentoUpdate = alimentoRepository.save(alimento);
        return mapearDTO(alimentoUpdate);
    }


    private AlimentoDTO mapearDTO(Alimento alimento) {
        AlimentoDTO alimentoDTO = modelMapper.map(alimento, AlimentoDTO.class);
        return alimentoDTO;
    }

    private Alimento mapearEntity(AlimentoDTO alimentoDTO) {
        Alimento alimento = modelMapper.map(alimentoDTO, Alimento.class);
        return alimento;
    }
}
