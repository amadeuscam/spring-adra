package com.amadeuscam.adratorrejon.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amadeuscam.adratorrejon.dto.BeneficiarioDTO;
import com.amadeuscam.adratorrejon.dto.BeneficiarioResponse;
import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Beneficiario;
import com.amadeuscam.adratorrejon.models.Familiar;
import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;
import com.amadeuscam.adratorrejon.repository.FamiliarRepository;

@Service
public class BeneficiarioImpl implements BeneficiarioService {
        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private BeneficiarioRepository beneficiarioRepository;
        @Autowired
        private FamiliarRepository familiarRepository;

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

                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();
                Pageable pageable = PageRequest.of(numberPage, pageSize, sort);

                Page<Beneficiario> beneficiarios = beneficiarioRepository.findAll(pageable);

                List<Beneficiario> beneficiarioList = beneficiarios.getContent();

                List<BeneficiarioDTO> contenido = beneficiarioList.stream().map(beneficiario -> mapearDTO(beneficiario))
                                .collect(Collectors.toList());
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
        public BeneficiarioDTO getBeneficiarioById(UUID id) {
                Beneficiario beneficiario = beneficiarioRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", id.toString()));
                return mapearDTO(beneficiario);
        }

        @Override
        public BeneficiarioDTO updateBeneficiario(BeneficiarioDTO beneficiarioDTO, UUID id) {
                Beneficiario beneficiario = beneficiarioRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFound("Publicacion", "id", id.toString()));

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
        public void deleteBeneficiaioById(UUID id) {
                Beneficiario beneficiario = beneficiarioRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", id.toString()));
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

        @Override
        public Map<String, Long> getAllBeneficiariosEstatisticas() {

                Map<String, Long> map_ben = new HashMap<String, Long>();
                List<Beneficiario> beneficiario = beneficiarioRepository.findAll();
                List<Familiar> familiars = familiarRepository.findAll();

                Long men_0_2 = beneficiario.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 0, 2) && p.getSexo().equals("h")).count();
                Long fam_0_2 = familiars.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 0, 2) && p.getSexo().equals("h"))
                                .count();
                Long men_3_15 = beneficiario.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 3, 15) && p.getSexo().equals("h"))
                                .count();
                Long fam_3_15 = familiars.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 3, 15) && p.getSexo().equals("h"))
                                .count();
                Long men_16_64 = beneficiario.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 16, 64) && p.getSexo().equals("h"))
                                .count();
                Long fam_16_64 = familiars.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 16, 64) && p.getSexo().equals("h"))
                                .count();
                Long men_gt_65 = beneficiario.stream().filter(p -> p.getEdad() >= 65 && p.getSexo().equals("h"))
                                .count();
                Long fam_gt_65 = familiars.stream().filter(p -> p.getEdad() >= 65 && p.getSexo().equals("h")).count();

                Long fmale_0_2 = beneficiario.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 0, 2) && p.getSexo().equals("m"))
                                .count();
                Long fam_fmale_0_2 = familiars.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 0, 2) && p.getSexo().equals("m"))
                                .count();
                Long fmale_3_15 = beneficiario.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 3, 15) && p.getSexo().equals("m"))
                                .count();
                Long fam_fmale_3_15 = familiars.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 3, 15) && p.getSexo().equals("m"))
                                .count();
                Long fmale_16_64 = beneficiario.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 16, 64) && p.getSexo().equals("m"))
                                .count();
                Long fam_fmale_16_64 = familiars.stream()
                                .filter(p -> ageRange(p.getFechanacimiento(), 16, 64) && p.getSexo().equals("m"))
                                .count();
                Long fmale_gt_65 = beneficiario.stream().filter(p -> p.getEdad() >= 65 && p.getSexo().equals("m"))
                                .count();
                Long fam_fmale_gt_65 = familiars.stream().filter(p -> p.getEdad() >= 65 && p.getSexo().equals("m"))
                                .count();

                // map_ben.put("beneficarios_hombres_0_2", men_0_2);
                // map_ben.put("beneficarios_hombres_3_15", men_3_15);
                // map_ben.put("beneficarios_hombres_16_64", men_16_64);
                // map_ben.put("beneficarios_hombres_gt_65", men_gt_65);

                // map_ben.put("beneficarios_mujeres_0_2", fmale_0_2);
                // map_ben.put("beneficarios_mujeres_3_15", fmale_3_15);
                // map_ben.put("beneficarios_mujeres_16_64", fmale_16_64);
                // map_ben.put("beneficarios_mujeres_gt_65", fmale_gt_65);

                // map_ben.put("familiares_hombres_0_2", fam_0_2);
                // map_ben.put("familiares_hombres_3_15", fam_3_15);
                // map_ben.put("familiares_hombres_16_64", fam_16_64);
                // map_ben.put("familiares_hombres_gt_65", fam_gt_65);

                // map_ben.put("familiares_mujeres_0_2", fam_fmale_0_2);
                // map_ben.put("familiares_mujeres_3_15", fam_fmale_3_15);
                // map_ben.put("familiares_mujeres_16_64", fam_fmale_16_64);
                // map_ben.put("familiares_mujeres_gt_65", fam_fmale_gt_65);

                long total_02_mujeres = fmale_0_2 + fam_fmale_0_2;
                long total_3_15_mujeres = fmale_3_15 + fam_fmale_3_15;
                long total_16_64_mujeres = fmale_16_64 + fam_fmale_16_64;
                long total_gt_65_mujeres = fmale_gt_65 + fam_fmale_gt_65;
                long total_mujeres = total_02_mujeres + total_3_15_mujeres + total_16_64_mujeres + total_gt_65_mujeres;

                map_ben.put("total_02_mujeres", total_02_mujeres);
                map_ben.put("total_3_15_mujeres", total_3_15_mujeres);
                map_ben.put("total_16_64_mujeres", total_16_64_mujeres);
                map_ben.put("total_gt_65_mujeres", total_gt_65_mujeres);
                map_ben.put("total_mujeres", total_mujeres);

                long total_02_hombres = men_0_2 + fam_0_2;
                long total_3_15_hombres = men_3_15 + fam_3_15;
                long total_16_64_hombres = men_16_64 + fam_16_64;
                long total_gt_65_hombres = men_gt_65 + fam_gt_65;
                long total_hombres = total_02_hombres + total_3_15_hombres + total_16_64_hombres + total_gt_65_hombres;

                map_ben.put("total_02_hombres", total_02_hombres);
                map_ben.put("total_3_15_hombres", total_3_15_hombres);
                map_ben.put("total_16_64_hombres", total_16_64_hombres);
                map_ben.put("total_gt_65_hombres", total_gt_65_hombres);
                map_ben.put("total_hombres", total_hombres);

                map_ben.put("total_02_hombres_mujeres", total_02_hombres + total_02_mujeres);
                map_ben.put("total_3_15_hombres_mujeres", total_3_15_hombres + total_3_15_mujeres);
                map_ben.put("total_16_64_hombres_mujeres", total_16_64_hombres + total_16_64_mujeres);
                map_ben.put("total_gt_65_hombres_mujeres", total_gt_65_hombres + total_gt_65_mujeres);

                map_ben.put("total_hombres_mujeres", total_hombres + total_mujeres);
                map_ben.put("beneficiarios", (long) beneficiario.size());
                map_ben.put("familiars", (long) familiars.size());
                map_ben.put("discapacidad", beneficiario.stream().filter(p -> p.isDiscapacidad()).count());

                return map_ben;
        }

        public boolean ageRange(LocalDate birthDate, Integer min, Integer max) {
                Integer years = Period.between(birthDate, LocalDate.now()).getYears();
                return years >= min && years <= max;
        }
}
