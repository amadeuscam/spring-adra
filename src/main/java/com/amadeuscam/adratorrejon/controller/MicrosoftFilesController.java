package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Beneficiario;
import com.amadeuscam.adratorrejon.models.Familiar;
import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class MicrosoftFilesController {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;


    @GetMapping("/valoracion-social/{id}")
    public ResponseEntity<byte[]> generateHojaDeEntrega(@PathVariable(value = "id") UUID id) throws Exception {
        Beneficiario beneficiario = beneficiarioRepository.
                findById(id).orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", id.toString()));
        String docxFile = "src/main/resources/resources_files/vl.docx";
        File docx = new File(docxFile);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFDocument doc = new XWPFDocument(new FileInputStream(docx));
        int placeHolderRowPosition = 1;
        // recorer el cuerpo de docx
        for (XWPFParagraph p : doc.getParagraphs()) {
            for (XWPFRun run : p.getRuns()) {
                int pos = run.getTextPosition();
                String text = run.getText(pos);
                if (text != null && text.contains("«numar_telefon»")) {
                    text = text.replace("«numar_telefon»", String.valueOf(beneficiario.getTelefono()));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«numar_adra»")) {
                    text = text.replace("«numar_adra»", String.valueOf(beneficiario.getNumeroadra()));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«nombre_apellido»")) {
                    text = text.replace("«nombre_apellido»", String.valueOf(beneficiario.getNombreapellido()));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«dni»")) {
                    text = text.replace("«dni»", String.valueOf(!beneficiario.getDni().isEmpty() ? beneficiario.getDni() : beneficiario.getOtrosdocumentos()));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«fecha_nacimiento»")) {
                    text = text.replace("«fecha_nacimiento»", String.valueOf(beneficiario.getFechanacimiento().format(formatter)));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«nacionalidad»")) {
                    text = text.replace("«nacionalidad»", String.valueOf(beneficiario.getNacionalidad()));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«domicilio»")) {
                    text = text.replace("«domicilio»", String.valueOf(beneficiario.getDomicilio()));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«ciudad»")) {
                    text = text.replace("«ciudad»", String.valueOf(beneficiario.getCiudad()));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«email»")) {
                    text = text.replace("«email»", "");
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«fecha_hoy»")) {
                    text = text.replace("«fecha_hoy»", "");
                    run.setText(text, 0);
                }
            }

        }
        // tabla documentacion
        XWPFTable table_documentacion = doc.getTableArray(2);
        List<XWPFTableRow> rowList = table_documentacion.getRows();
        for (XWPFTableRow row : rowList) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    for (XWPFRun run : paragraph.getRuns()) {
                        int pos = run.getTextPosition();
                        String text = run.getText(pos);
//                        System.out.println(text);
                        if (text != null && text.contains("«a»")) {
                            text = text.replace("«a»", beneficiario.isEmpadronamiento() ? "x" : "");
                            run.setText(text, 0);
                        }
                        if (text != null && text.trim().contains("«b»")) {
                            text = text.replace("«b»", beneficiario.isLibrofamilia() ? "x" : "");
                            run.setText(text, 0);
                        }
                        if (text != null && text.trim().contains("«c»")) {
                            text = text.replace("«c»", beneficiario.isAreacte() ? "x" : "");
                            run.setText(text, 0);
                        }
                        if (text != null && text.trim().contains("«d»")) {
                            text = text.replace("«d»", beneficiario.isPrestaciones() ? "x" : "");
                            run.setText(text, 0);
                        }
                        if (text != null && text.trim().contains("«e»")) {
                            text = text.replace("«e»", beneficiario.isNomnia() ? "x" : "");
                            run.setText(text, 0);
                        }
                        if (text != null && text.trim().contains("«f»")) {
                            text = text.replace("«f»", beneficiario.isCertnegativo() ? "x" : "");
                            run.setText(text, 0);
                        }
                        if (text != null && text.trim().contains("«g»")) {
                            text = text.replace("«g»", beneficiario.isAquilerhipoteca() ? "x" : "");
                            run.setText(text, 0);
                        }
                        if (text != null && text.trim().contains("«h»")) {
                            text = text.replace("«h»", beneficiario.isRecibos() ? "x" : "");
                            run.setText(text, 0);
                        }
                    }
                }

            }

        }

        // tabla familiares
        XWPFTable table_familiares = doc.getTableArray(0);
        XWPFTableRow oldRow = table_familiares.getRow(placeHolderRowPosition);
        int rowsAdded = 2; // Start at "2" which is really third position
        for (Familiar familiar : beneficiario.getFamiliares()) {
            CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
            XWPFTableRow newRow = new XWPFTableRow(ctrow, table_familiares);

            for (XWPFTableCell cell : newRow.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    for (XWPFRun run : paragraph.getRuns()) {
                        String textToInsert = switch (run.text().trim()) {
                            case "«parentesco»" -> familiar.getParentesco();
                            case "«nombre_apellido_hijo»" -> familiar.getNombreapellido();
                            case "«dni_hijo»" -> familiar.getDni();
                            case "«pasaporte_hijo»" -> familiar.getOtros_documentos();
                            case "«fecha_nacimiento_hijo»" -> familiar.getFechanacimiento().format(formatter);
                            default -> "";
                        };
                        run.setText(textToInsert, 0);
                    }
                }

            }

            table_familiares.addRow(newRow, rowsAdded);
            rowsAdded++;
        }

        // Descomentar si no queremos que salgan los placeholders
        table_familiares.removeRow(placeHolderRowPosition);
        doc.write(out);
        doc.close();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=das.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)).body(out.toByteArray());


//        return null;
    }

}
