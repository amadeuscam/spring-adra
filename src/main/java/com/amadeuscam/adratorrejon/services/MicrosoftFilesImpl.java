package com.amadeuscam.adratorrejon.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Beneficiario;
import com.amadeuscam.adratorrejon.models.Familiar;
import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;

@Service
public class MicrosoftFilesImpl implements MicrosoftFilesService {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Override
    public ByteArrayOutputStream generateExcelBeneficarios() {

        List<Beneficiario> beneficiarios = beneficiarioRepository.findAll();
        String[] columns = {
                "Numero adra",
                "Nombre",
                "Representante Familiar",
                "Dni",
                "Pasaporte",
                "Fecha de nacimiento"
        };

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Employee");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK1.getIndex());

        CellStyle cs = workbook.createCellStyle();
        cs.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cs.setFont(headerFont);
        // cs.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // backgroundStyle.setFillPattern(CellStyle.);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            // cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle styleDateBen = extracted(workbook, createHelper, headerFont, true);
        CellStyle styleDateFam = extracted(workbook, createHelper, headerFont, false);

        // CellStyle dateCellStylefam = workbook.createCellStyle();
        // dateCellStylefam.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for (Beneficiario beneficiario : beneficiarios) {
            Row row = sheet.createRow(rowNum++);
            // System.out.println(beneficiario.getFamiliares());
            for (Familiar familiar : beneficiario.getFamiliares()) {
                Row row_fam = sheet.createRow(rowNum++);
                System.out.println(familiar.getNombreapellido());
                // row.createCell(0).setCellValue(beneficiario.getNumeroadra());

                row_fam.createCell(1).setCellValue(familiar.getNombreapellido());
                // row.createCell(2).setCellValue(1);
                row_fam.createCell(3).setCellValue(familiar.getDni());
                row_fam.createCell(4).setCellValue(familiar.getOtros_documentos());

                Cell dateOfBirthCell = row_fam.createCell(5);
                dateOfBirthCell.setCellValue(familiar.getFechanacimiento());
                dateOfBirthCell.setCellStyle(styleDateFam);

            }

            row.createCell(0).setCellValue(beneficiario.getNumeroadra());

            row.createCell(1).setCellValue(beneficiario.getNombreapellido());
            row.createCell(2).setCellValue(1);
            row.createCell(3).setCellValue(beneficiario.getDni());
            row.createCell(4).setCellValue(beneficiario.getOtrosdocumentos());
            // row.createCell(5).setCellValue(beneficiario.getFechanacimiento());

            Cell dateOfBirthCell = row.createCell(5);
            dateOfBirthCell.setCellValue(beneficiario.getFechanacimiento());
            dateOfBirthCell.setCellStyle(styleDateBen);

            row.getCell(0).setCellStyle(cs);
            row.getCell(1).setCellStyle(cs);
            row.getCell(2).setCellStyle(cs);
            row.getCell(3).setCellStyle(cs);
            row.getCell(4).setCellStyle(cs);
            // row.getCell(5).setCellStyle(cs);
            // row.getCell(row.ce)
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Write the output to a file
        // FileOutputStream fileOut = new FileOutputStream("poi-generated-file.xlsx");
        try {
            workbook.write(out);
            // Closing the workbook
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

    @Override
    public ByteArrayOutputStream generateHojaDeValoracion(UUID id) throws XmlException, IOException {

        Beneficiario beneficiario = beneficiarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", id.toString()));
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
                    text = text.replace("«dni»", String.valueOf(!beneficiario.getDni().isEmpty() ? beneficiario.getDni()
                            : beneficiario.getOtrosdocumentos()));
                    run.setText(text, 0);
                }
                if (text != null && text.contains("«fecha_nacimiento»")) {
                    text = text.replace("«fecha_nacimiento»",
                            String.valueOf(beneficiario.getFechanacimiento().format(formatter)));
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
                        // System.out.println(text);
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

        return out;
    }

    private CellStyle extracted(Workbook workbook, CreationHelper createHelper, Font headerFont,
            Boolean setBackground) {
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        if (setBackground) {
            dateCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            dateCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            dateCellStyle.setFont(headerFont);
        }

        return dateCellStyle;
    }

}
