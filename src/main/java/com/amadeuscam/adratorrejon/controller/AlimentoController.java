package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.dto.AlimentoDTO;
import com.amadeuscam.adratorrejon.dto.FamiliarDTO;
import com.amadeuscam.adratorrejon.services.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping("/beneficiarios/{id}/alimentos")
    public List<AlimentoDTO> allAlimentosPerBeneficiario(@PathVariable(value = "id") Long id) {
        return alimentoService.getAllAlimentosPerBeneficarioId(id);
    }

    @GetMapping("/beneficiarios/{ben_id}/alimentos/{al_id}")
    public ResponseEntity<AlimentoDTO> getAlimentosById(@PathVariable(value = "ben_id") Long ben_id,
                                                        @PathVariable(value = "al_id") Long al_id) {
        AlimentoDTO alimentoDTO = alimentoService.getAlimentosById(ben_id, al_id);
        return new ResponseEntity<>(alimentoDTO, HttpStatus.OK);

    }

    @PostMapping("/beneficiarios/{id}/alimentos")
    public ResponseEntity<AlimentoDTO> saveAlimentos(@PathVariable(value = "id") long id,
                                                     @Valid @RequestBody AlimentoDTO alimentoDTO) {
        return new ResponseEntity<>(alimentoService.createAlimento(id, alimentoDTO), HttpStatus.CREATED);

    }

    @PutMapping("/beneficiarios/{ben_id}/alimentos/{al_id}")
    public ResponseEntity<AlimentoDTO> updateAlimentosById(@PathVariable(value = "ben_id") Long ben_id,
                                                           @Valid @RequestBody AlimentoDTO alimentoDTO,
                                                           @PathVariable(value = "fam_id") Long al_id) {

        return new ResponseEntity<>(alimentoService.updateAlimentos(alimentoDTO, ben_id, al_id), HttpStatus.OK);
    }

    @DeleteMapping("/beneficiarios/{ben_id}/alimentos/{al_id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "ben_id") Long ben_id,
                                                    @PathVariable(value = "al_id") Long al_id) {
        alimentoService.deleteAlimentos(ben_id, al_id);
        return new ResponseEntity<>("Se ha borado los alimentos con exito!", HttpStatus.OK);
    }
}
