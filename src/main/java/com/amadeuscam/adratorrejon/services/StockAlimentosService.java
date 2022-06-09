package com.amadeuscam.adratorrejon.services;

import com.amadeuscam.adratorrejon.dto.StockAlimentoDTO;

public interface StockAlimentosService {

    public StockAlimentoDTO addAlimento(StockAlimentoDTO alimentoDTO);

    public StockAlimentoDTO getAlimentosById(Long alimento_id);

    public void deleteAlimentos(Long alimento_id);

    public StockAlimentoDTO updateAlimentos(Long alimento_id, StockAlimentoDTO alimentoDTO);

}
