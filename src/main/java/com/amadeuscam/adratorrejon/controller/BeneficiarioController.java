package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.dto.BeneficiarioDTO;
import com.amadeuscam.adratorrejon.dto.BeneficiarioResponse;
import com.amadeuscam.adratorrejon.services.BeneficiarioService;
import com.amadeuscam.adratorrejon.utils.AppConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beneficiarios")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioService beneficiarioService;

    @ApiOperation("Muestra la lista de beneficiarios")
    @GetMapping
    public BeneficiarioResponse lstBeneficiarios(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_DEFAULT, required = false) int numPage,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_DEFAULT, required = false) int pagSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PAGE_SORTBY_DEFAULT, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.PAGE_SORTDIR_DEFAULT, required = false) String sortDir) {

        return beneficiarioService.getAllBeneficiaios(numPage, pagSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiarioDTO> getBeneficiario(@PathVariable(name = "id") String id) {
        System.out.println(UUID.fromString(id));
        return ResponseEntity.ok(beneficiarioService.getBeneficiarioById(UUID.fromString(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BeneficiarioDTO> saveBeneficiario(@Valid @RequestBody BeneficiarioDTO beneficiarioDTO) {
        return new ResponseEntity<>(beneficiarioService.createBeneficiario(beneficiarioDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BeneficiarioDTO> updateBeneficiarioById(
            @PathVariable(value = "id") UUID ben_id,
            @Valid @RequestBody BeneficiarioDTO beneficiarioDTO) {

        return new ResponseEntity<>(beneficiarioService.updateBeneficiario(beneficiarioDTO, ben_id), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublicacion(@PathVariable(name = "id") UUID id) {
        beneficiarioService.deleteBeneficiaioById(id);
        return new ResponseEntity<>(Collections.singletonMap("response", "Se ha borado el beneficario con exito!"), HttpStatus.OK);
    }
}
