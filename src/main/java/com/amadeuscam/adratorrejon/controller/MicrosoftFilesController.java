package com.amadeuscam.adratorrejon.controller;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeuscam.adratorrejon.services.MicrosoftFilesService;

@RestController
@RequestMapping("/api/v1/")
public class MicrosoftFilesController {

    @Autowired
    private MicrosoftFilesService microsoftFilesService;

    @GetMapping("/valoracion-social/{id}")
    public ResponseEntity<byte[]> generateHojaDeEntrega(@PathVariable(value = "id") UUID id) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out = microsoftFilesService.generateHojaDeValoracion(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=das.pdf");
        return ResponseEntity.ok().headers(headers)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)).body(out.toByteArray());

    }

    @GetMapping("/beneficarios-excel")
    public ResponseEntity<byte[]> generateHojaDeExcel() {
        ByteArrayOutputStream out = microsoftFilesService.generateExcelBeneficarios();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=beneficiarios.xlsx");
        return ResponseEntity.ok().headers(headers)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)).body(out.toByteArray());

    }

}
