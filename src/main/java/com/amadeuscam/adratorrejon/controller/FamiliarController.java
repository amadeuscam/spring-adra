package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.dto.FamiliarDTO;
import com.amadeuscam.adratorrejon.services.FamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class FamiliarController {

    @Autowired
    private FamiliarService familiarService;

    @GetMapping("/beneficiarios/{id}/familiares")
    public List<FamiliarDTO> allFamiliaresPerBeneficiario(@PathVariable(value = "id") UUID id) {
        return familiarService.getAllFamiliaresPerBeneficarioId(id);
    }

    @GetMapping("/beneficiarios/{ben_id}/familiares/{fam_id}")
    public ResponseEntity<FamiliarDTO> getFamiliarById(@PathVariable(value = "ben_id") UUID ben_id,
                                                       @PathVariable(value = "fam_id") Long fam_id) {
        FamiliarDTO familiarDTO = familiarService.getFamiliarById(ben_id, fam_id);
        return new ResponseEntity<>(familiarDTO, HttpStatus.OK);

    }

    @PostMapping("/beneficiarios/{id}/familiares")
    public ResponseEntity<FamiliarDTO> saveFamiliar(@PathVariable(value = "id") UUID id,
                                                    @Valid @RequestBody FamiliarDTO familiarDTO) {
        return new ResponseEntity<>(familiarService.createFamiliar(id, familiarDTO), HttpStatus.CREATED);

    }

    @PutMapping("/beneficiarios/{ben_id}/familiares/{fam_id}")
    public ResponseEntity<FamiliarDTO> updateFamiliarById(@PathVariable(value = "ben_id") UUID ben_id,
                                                          @Valid @RequestBody FamiliarDTO FamiliarDTO,
                                                          @PathVariable(value = "fam_id") Long fam_id) {

        return new ResponseEntity<>(familiarService.updateFamiliar(FamiliarDTO, ben_id, fam_id), HttpStatus.OK);
    }

    @DeleteMapping("/beneficiarios/{ben_id}/familiares/{fam_id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable(value = "ben_id") UUID ben_id,
                                                    @PathVariable(value = "fam_id") Long fam_id) {
        familiarService.deleteFamiliar(ben_id, fam_id);
        return new ResponseEntity<>(Collections.singletonMap("response", "Se ha borado el familiar con exito!"), HttpStatus.OK);
    }
}
