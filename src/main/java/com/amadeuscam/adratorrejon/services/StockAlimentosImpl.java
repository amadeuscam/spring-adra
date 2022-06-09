package com.amadeuscam.adratorrejon.services;

import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amadeuscam.adratorrejon.dto.StockAlimentoDTO;
import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.StockAlimentos;
import com.amadeuscam.adratorrejon.repository.StockAlimentosRepository;

@Service
public class StockAlimentosImpl implements StockAlimentosService {

    @Autowired
    private StockAlimentosRepository stockAlimentosRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StockAlimentoDTO addAlimento(StockAlimentoDTO alimentoDTO) {
        StockAlimentos alimento = mapearEntity(alimentoDTO);
        StockAlimentos newAlimentos = stockAlimentosRepository.save(alimento);
        return mapearDTO(newAlimentos);
    }

    @Override
    public StockAlimentoDTO getAlimentosById(Long alimento_id) {
        StockAlimentos alimento = stockAlimentosRepository.findById(alimento_id)
                .orElseThrow(() -> new ResourceNotFound("Alimento", "id", String.valueOf(alimento_id)));
        return mapearDTO(alimento);
    }

    @Override
    public void deleteAlimentos(Long alimento_id) {
        // TODO Auto-generated method stub

    }

    @Override
    public StockAlimentoDTO updateAlimentos(Long alimento_id, StockAlimentoDTO alimentoDTO) {
        StockAlimentos alimento = stockAlimentosRepository.findById(alimento_id)
                .orElseThrow(() -> new ResourceNotFound("Stock Alimento", "id", String.valueOf(alimento_id)));
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
        
        alimento.setAlimento1_cad(alimentoDTO.getAlimento1_cad());
        alimento.setAlimento2_cad(alimentoDTO.getAlimento2_cad());
        alimento.setAlimento3_cad(alimentoDTO.getAlimento3_cad());
        alimento.setAlimento4_cad(alimentoDTO.getAlimento4_cad());
        alimento.setAlimento5_cad(alimentoDTO.getAlimento5_cad());
        alimento.setAlimento6_cad(alimentoDTO.getAlimento6_cad());
        alimento.setAlimento7_cad(alimentoDTO.getAlimento7_cad());
        alimento.setAlimento8_cad(alimentoDTO.getAlimento8_cad());
        alimento.setAlimento9_cad(alimentoDTO.getAlimento9_cad());
        alimento.setAlimento10_cad(alimentoDTO.getAlimento10_cad());
        alimento.setAlimento11_cad(alimentoDTO.getAlimento11_cad());
        alimento.setAlimento12_cad(alimentoDTO.getAlimento12_cad());
        alimento.setAlimento13_cad(alimentoDTO.getAlimento13_cad());
        alimento.setUpdatedDate(Instant.now());
        StockAlimentos alimentoUpdate = stockAlimentosRepository.save(alimento);
        return mapearDTO(alimentoUpdate);

    }

    private StockAlimentoDTO mapearDTO(StockAlimentos alimento) {
        StockAlimentoDTO alimentoDTO = modelMapper.map(alimento, StockAlimentoDTO.class);
        return alimentoDTO;
    }

    private StockAlimentos mapearEntity(StockAlimentoDTO alimentoDTO) {
        StockAlimentos alimento = modelMapper.map(alimentoDTO, StockAlimentos.class);
        return alimento;
    }

}
