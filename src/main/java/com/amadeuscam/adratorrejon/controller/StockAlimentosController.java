package com.amadeuscam.adratorrejon.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeuscam.adratorrejon.dto.StockAlimentoDTO;
import com.amadeuscam.adratorrejon.services.StockAlimentosService;

 

@RestController
@RequestMapping("/api/v1/")
public class StockAlimentosController {

    @Autowired
    private StockAlimentosService alimentoService;

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/stock-alimentos/{id}")
    public ResponseEntity<StockAlimentoDTO> saveAlimentos(@Valid @RequestBody StockAlimentoDTO stockDTO,@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(alimentoService.updateAlimentos(id,stockDTO), HttpStatus.CREATED);
    }

    @GetMapping("/stock-alimentos/{id}")
    public ResponseEntity<?> allAlimentos(@PathVariable(value = "id") Long id) {
       return  new ResponseEntity<>(Collections.singletonMap("response",alimentoService.getAlimentosById(id) ), HttpStatus.CREATED);
    }

}
