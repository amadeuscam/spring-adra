package com.amadeuscam.adratorrejon.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amadeuscam.adratorrejon.dto.AlimentoDTO;
import com.amadeuscam.adratorrejon.exceptions.AdraAppException;
import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Alimento;
import com.amadeuscam.adratorrejon.models.Beneficiario;
import com.amadeuscam.adratorrejon.models.StockAlimentos;
import com.amadeuscam.adratorrejon.repository.AlimentoRepository;
import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;
import com.amadeuscam.adratorrejon.repository.StockAlimentosRepository;

@Service
public class AlimentoImpl implements AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private StockAlimentosRepository stockAlimentosRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BeneficiarioRepository beneficiarioRepository;

    @Override
    public AlimentoDTO createAlimento(UUID beneficiario_id, AlimentoDTO alimentoDTO) {
        Alimento alimento = mapearEntity(alimentoDTO);
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id)
                .orElseThrow(() -> new ResourceNotFound("Publicacion", "id", beneficiario_id.toString()));
        // cogemos siempre la primera row de la table
        StockAlimentos stockAlimentos = stockAlimentosRepository.getById((long) 1);
        // restamos los alimentos que repartimos a las personas
        stockAlimentos.setAlimento1(stockAlimentos.getAlimento1() - alimento.getAlimento1());
        stockAlimentos.setAlimento2(stockAlimentos.getAlimento2() - alimento.getAlimento2());
        stockAlimentos.setAlimento3(stockAlimentos.getAlimento3() - alimento.getAlimento3());
        stockAlimentos.setAlimento4(stockAlimentos.getAlimento4() - alimento.getAlimento4());
        stockAlimentos.setAlimento5(stockAlimentos.getAlimento5() - alimento.getAlimento5());
        stockAlimentos.setAlimento6(stockAlimentos.getAlimento6() - alimento.getAlimento6());
        stockAlimentos.setAlimento7(stockAlimentos.getAlimento7() - alimento.getAlimento7());
        stockAlimentos.setAlimento8(stockAlimentos.getAlimento8() - alimento.getAlimento8());
        stockAlimentos.setAlimento9(stockAlimentos.getAlimento9() - alimento.getAlimento9());
        stockAlimentos.setAlimento10(stockAlimentos.getAlimento10() - alimento.getAlimento10());
        stockAlimentos.setAlimento11(stockAlimentos.getAlimento11() - alimento.getAlimento11());
        stockAlimentos.setAlimento12(stockAlimentos.getAlimento12() - alimento.getAlimento12());
        stockAlimentos.setAlimento13(stockAlimentos.getAlimento13() - alimento.getAlimento13());
        stockAlimentos.setUpdatedDate(Instant.now());
        alimento.setUpdatedDate(Instant.now());
        stockAlimentosRepository.save(stockAlimentos);
        alimento.setBeneficiario(beneficiario);
        Alimento newAlimentos = alimentoRepository.save(alimento);
        return mapearDTO(newAlimentos);
    }

    @Override
    public List<AlimentoDTO> getAllAlimentosPerBeneficarioId(UUID beneficiario_id) {
        List<Alimento> alimentos = alimentoRepository.findByBeneficiarioId(beneficiario_id);
        return alimentos.stream().map(alimento -> mapearDTO(alimento)).collect(Collectors.toList());
    }

    @Override
    public AlimentoDTO getAlimentosById(UUID beneficiario_id, long al_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id)
                .orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id.toString()));

        Alimento alimento = alimentoRepository.findById(al_id)
                .orElseThrow(() -> new ResourceNotFound("Alimento", "id", String.valueOf(al_id)));

        if (!alimento.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El Alimento no tiene beneficiario");
        }
        return mapearDTO(alimento);
    }

    @Override
    public void deleteAlimentos(UUID beneficiario_id, long al_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id)
                .orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id.toString()));

        Alimento alimento = alimentoRepository.findById(al_id)
                .orElseThrow(() -> new ResourceNotFound("Alimento", "id", String.valueOf(al_id)));

        if (!alimento.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El Alimento no tiene beneficiario");
        }
        alimentoRepository.delete(alimento);
    }

    public AlimentoDTO updateAlimentos(AlimentoDTO alimentoDTO, UUID beneficiario_id, Long al_id) {
        Beneficiario beneficiario = beneficiarioRepository.findById(beneficiario_id)
                .orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", beneficiario_id.toString()));

        Alimento alimento = alimentoRepository.findById(al_id)
                .orElseThrow(() -> new ResourceNotFound("Alimento", "id", String.valueOf(al_id)));

        if (!alimento.getBeneficiario().getId().equals(beneficiario.getId())) {
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "El Alimento no tiene beneficiario");
        }
        // nos traemos el stock de los alimentos
        StockAlimentos stockAlimentos = stockAlimentosRepository.getById((long) 1);
        // aqui hacemos una comparacion entre el alimento en bdd y el que viene del front,
        // si sale a -1 hacemo un resta al alimento en bdd
        // si sale a 0 no hacemo nada
        // si sale a 1 hacemo un suma al alimento en bdd
        int state_alimento_1 = Integer.valueOf(alimento.getAlimento1()).compareTo(alimentoDTO.getAlimento1());
        int state_alimento_2 = Integer.valueOf(alimento.getAlimento2()).compareTo(alimentoDTO.getAlimento2());
        int state_alimento_3 = Integer.valueOf(alimento.getAlimento3()).compareTo(alimentoDTO.getAlimento3());
        int state_alimento_4 = Integer.valueOf(alimento.getAlimento4()).compareTo(alimentoDTO.getAlimento4());
        int state_alimento_5 = Integer.valueOf(alimento.getAlimento5()).compareTo(alimentoDTO.getAlimento5());
        int state_alimento_6 = Integer.valueOf(alimento.getAlimento6()).compareTo(alimentoDTO.getAlimento6());
        int state_alimento_7 = Integer.valueOf(alimento.getAlimento7()).compareTo(alimentoDTO.getAlimento7());
        int state_alimento_8 = Integer.valueOf(alimento.getAlimento8()).compareTo(alimentoDTO.getAlimento8());
        int state_alimento_9 = Integer.valueOf(alimento.getAlimento9()).compareTo(alimentoDTO.getAlimento9());
        int state_alimento_10 = Integer.valueOf(alimento.getAlimento10()).compareTo(alimentoDTO.getAlimento10());
        int state_alimento_11 = Integer.valueOf(alimento.getAlimento11()).compareTo(alimentoDTO.getAlimento11());
        int state_alimento_12 = Integer.valueOf(alimento.getAlimento12()).compareTo(alimentoDTO.getAlimento12());
        int state_alimento_13 = Integer.valueOf(alimento.getAlimento13()).compareTo(alimentoDTO.getAlimento13());

        if (state_alimento_1 == -1) {
            System.out.println("base de bdd es menor ");
            int diff = (alimentoDTO.getAlimento1() - alimento.getAlimento1());
            stockAlimentos.setAlimento1(stockAlimentos.getAlimento1() - diff);
            System.out.println(diff);

        } else if (state_alimento_1 == 0) {
            System.out.println("son iguales");
            int diff = (alimento.getAlimento1() - alimentoDTO.getAlimento1());
            System.out.println(diff);
        } else {
            System.out.println("base de bdd mas grande ");
            int diff = (alimento.getAlimento1() - alimentoDTO.getAlimento1());
            stockAlimentos.setAlimento1(stockAlimentos.getAlimento1() + diff);
            System.out.println(diff);
        }

        if (state_alimento_2 == -1) {
            int diff = (alimentoDTO.getAlimento2() - alimento.getAlimento2());
            stockAlimentos.setAlimento2(stockAlimentos.getAlimento2() - diff);
        } else if (state_alimento_2 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento2() - alimentoDTO.getAlimento2());
            stockAlimentos.setAlimento2(stockAlimentos.getAlimento2() + diff);
        }

        if (state_alimento_3 == -1) {
            int diff = (alimentoDTO.getAlimento3() - alimento.getAlimento3());
            stockAlimentos.setAlimento3(stockAlimentos.getAlimento3() - diff);
        } else if (state_alimento_3 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento3() - alimentoDTO.getAlimento3());
            stockAlimentos.setAlimento3(stockAlimentos.getAlimento3() + diff);
        }

        if (state_alimento_4 == -1) {
            int diff = (alimentoDTO.getAlimento4() - alimento.getAlimento4());
            stockAlimentos.setAlimento4(stockAlimentos.getAlimento4() - diff);
        } else if (state_alimento_4 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento4() - alimentoDTO.getAlimento4());
            stockAlimentos.setAlimento4(stockAlimentos.getAlimento4() + diff);
        }

        if (state_alimento_5 == -1) {
            int diff = (alimentoDTO.getAlimento5() - alimento.getAlimento5());
            stockAlimentos.setAlimento5(stockAlimentos.getAlimento5() - diff);
        } else if (state_alimento_5 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento5() - alimentoDTO.getAlimento5());
            stockAlimentos.setAlimento5(stockAlimentos.getAlimento5() + diff);
        }

        if (state_alimento_6 == -1) {
            int diff = (alimentoDTO.getAlimento6() - alimento.getAlimento6());
            stockAlimentos.setAlimento6(stockAlimentos.getAlimento6() - diff);
        } else if (state_alimento_6 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento6() - alimentoDTO.getAlimento6());
            stockAlimentos.setAlimento6(stockAlimentos.getAlimento6() + diff);
        }

        if (state_alimento_7 == -1) {
            int diff = (alimentoDTO.getAlimento7() - alimento.getAlimento7());
            stockAlimentos.setAlimento7(stockAlimentos.getAlimento7() - diff);
        } else if (state_alimento_7 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento7() - alimentoDTO.getAlimento7());
            stockAlimentos.setAlimento7(stockAlimentos.getAlimento7() + diff);
        }

        if (state_alimento_8 == -1) {
            int diff = (alimentoDTO.getAlimento7() - alimento.getAlimento8());
            stockAlimentos.setAlimento8(stockAlimentos.getAlimento8() - diff);
        } else if (state_alimento_8 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento8() - alimentoDTO.getAlimento8());
            stockAlimentos.setAlimento8(stockAlimentos.getAlimento8() + diff);
        }

        if (state_alimento_9 == -1) {
            int diff = (alimentoDTO.getAlimento9() - alimento.getAlimento9());
            stockAlimentos.setAlimento9(stockAlimentos.getAlimento9() - diff);
        } else if (state_alimento_9 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento9() - alimentoDTO.getAlimento9());
            stockAlimentos.setAlimento9(stockAlimentos.getAlimento9() + diff);
        }

        if (state_alimento_10 == -1) {
            int diff = (alimentoDTO.getAlimento10() - alimento.getAlimento10());
            stockAlimentos.setAlimento10(stockAlimentos.getAlimento10() - diff);
        } else if (state_alimento_10 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento10() - alimentoDTO.getAlimento10());
            stockAlimentos.setAlimento10(stockAlimentos.getAlimento10() + diff);
        }

        if (state_alimento_11 == -1) {
            int diff = (alimentoDTO.getAlimento11() - alimento.getAlimento11());
            stockAlimentos.setAlimento11(stockAlimentos.getAlimento11() - diff);
        } else if (state_alimento_11 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento11() - alimentoDTO.getAlimento11());
            stockAlimentos.setAlimento11(stockAlimentos.getAlimento11() + diff);
        }

        if (state_alimento_12 == -1) {
            int diff = (alimentoDTO.getAlimento12() - alimento.getAlimento12());
            stockAlimentos.setAlimento12(stockAlimentos.getAlimento12() - diff);
        } else if (state_alimento_12 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento12() - alimentoDTO.getAlimento12());
            stockAlimentos.setAlimento12(stockAlimentos.getAlimento12() + diff);
        }

        if (state_alimento_13 == -1) {
            int diff = (alimentoDTO.getAlimento13() - alimento.getAlimento13());
            stockAlimentos.setAlimento13(stockAlimentos.getAlimento13() - diff);
        } else if (state_alimento_13 == 0) {
            System.out.println("son iguales");
        } else {
            int diff = (alimento.getAlimento13() - alimentoDTO.getAlimento13());
            stockAlimentos.setAlimento13(stockAlimentos.getAlimento13() + diff);
        }

        stockAlimentos.setUpdatedDate(Instant.now());
        stockAlimentosRepository.save(stockAlimentos);

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
        alimento.setAlimento13(alimentoDTO.getAlimento13());
        alimento.setSignature(alimentoDTO.getSignature());
        alimento.setUpdatedDate(Instant.now());
        // alimento.setCreatedDate(Instant.now());
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
