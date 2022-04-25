package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.BeneficiarioDTO;
import com.amadeuscam.adratorrejon.dto.BeneficiarioResponse;
import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Beneficiario;
import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeneficiarioImpl implements BeneficiarioService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Override
    public BeneficiarioDTO createBeneficiario(BeneficiarioDTO beneficiarioDTO) {
        Beneficiario beneficiario = mapearEntity(beneficiarioDTO);
        Beneficiario newBeneficiario = beneficiarioRepository.save(beneficiario);
        return mapearDTO(newBeneficiario);
    }

    @Override
    public BeneficiarioResponse getAllBeneficiaios(int numberPage, int pageSize, String sortBy, String sortDir) {
        System.out.println(sortDir);
        System.out.println(Sort.Direction.ASC.name());
        System.out.println(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()));

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(numberPage, pageSize, sort);

        Page<Beneficiario> beneficiarios = beneficiarioRepository.findAll(pageable);

        List<Beneficiario> beneficiarioList = beneficiarios.getContent();

        List<BeneficiarioDTO> contenido = beneficiarioList.stream().map(beneficiario -> mapearDTO(beneficiario)).collect(Collectors.toList());
        BeneficiarioResponse beneficiarioResponse = new BeneficiarioResponse();
        beneficiarioResponse.setContenido(contenido);
        beneficiarioResponse.setPageNumber(beneficiarios.getNumber());
        beneficiarioResponse.setPageSize(beneficiarios.getSize());
        beneficiarioResponse.setTotalElements(beneficiarios.getTotalElements());
        beneficiarioResponse.setTotalPages(beneficiarios.getTotalPages());
        beneficiarioResponse.setLast(beneficiarios.isLast());

        return beneficiarioResponse;
    }

    @Override
    public BeneficiarioDTO getBeneficiarioById(long id) {
        Beneficiario beneficiario = beneficiarioRepository.
                findById(id).orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", id));
        return mapearDTO(beneficiario);
    }

    @Override
    public BeneficiarioDTO updateBeneficiario(BeneficiarioDTO beneficiarioDTO, long id) {
        Beneficiario beneficiario = beneficiarioRepository.
                findById(id).orElseThrow(() -> new ResourceNotFound("Publicacion", "id", id));

        beneficiario.setNombreapellido(beneficiarioDTO.getNombreapellido());
        beneficiario.setDni(beneficiarioDTO.getDni());
        beneficiario.setOtrosdocumentos(beneficiarioDTO.getOtrosdocumentos());
        beneficiario.setFechanacimiento(beneficiarioDTO.getFechanacimiento());
        beneficiario.setNumeroadra(beneficiarioDTO.getNumeroadra());
        beneficiario.setNacionalidad(beneficiarioDTO.getNacionalidad());
        beneficiario.setCovid(beneficiarioDTO.isCovid());
        beneficiario.setDomicilio(beneficiarioDTO.getDomicilio());
        beneficiario.setCiudad(beneficiarioDTO.getCiudad());
        beneficiario.setAreacte(beneficiarioDTO.isAreacte());
        beneficiario.setTelefono(beneficiarioDTO.getTelefono());
        beneficiario.setEmail(beneficiarioDTO.getEmail());
        beneficiario.setMensaje(beneficiarioDTO.getMensaje());
        beneficiario.setMensaje(beneficiarioDTO.getMensaje());
        beneficiario.setSexo(beneficiarioDTO.getSexo());
        beneficiario.setActive(beneficiarioDTO.isActive());
        beneficiario.setCategoria(beneficiarioDTO.getCategoria());
        beneficiario.setDiscapacidad(beneficiarioDTO.isDiscapacidad());
        beneficiario.setEmpadronamiento(beneficiarioDTO.isEmpadronamiento());
        beneficiario.setLibrofamilia(beneficiarioDTO.isLibrofamilia());
        beneficiario.setFotocopiadni(beneficiarioDTO.isFotocopiadni());
        beneficiario.setPrestaciones(beneficiarioDTO.isPrestaciones());
        beneficiario.setNomnia(beneficiarioDTO.isNomnia());
        beneficiario.setAquilerhipoteca(beneficiarioDTO.isAquilerhipoteca());
        beneficiario.setCertnegativo(beneficiarioDTO.isCertnegativo());
        beneficiario.setRecibos(beneficiarioDTO.isRecibos());


        Beneficiario publicacionUpdate = beneficiarioRepository.save(beneficiario);
        return mapearDTO(publicacionUpdate);

    }

    @Override
    public void deleteBeneficiaioById(long id) {
        Beneficiario beneficiario = beneficiarioRepository.
                findById(id).orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", id));
        beneficiarioRepository.delete(beneficiario);
    }

    private BeneficiarioDTO mapearDTO(Beneficiario beneficiario) {
        BeneficiarioDTO beneficiarioDTO = modelMapper.map(beneficiario, BeneficiarioDTO.class);
        return beneficiarioDTO;
    }

    private Beneficiario mapearEntity(BeneficiarioDTO beneficiarioDTO) {
        Beneficiario beneficiario = modelMapper.map(beneficiarioDTO, Beneficiario.class);
        return beneficiario;
    }
}
