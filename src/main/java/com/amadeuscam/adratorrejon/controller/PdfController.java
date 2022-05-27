package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.exceptions.ResourceNotFound;
import com.amadeuscam.adratorrejon.models.Alimento;
import com.amadeuscam.adratorrejon.models.Beneficiario;
import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class PdfController {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @GetMapping("/hoja-entrega/{id}")
    public ResponseEntity<byte[]> generateHojaDeEntrega(@PathVariable(value = "id") UUID id) {

        Beneficiario beneficiario = beneficiarioRepository.
                findById(id).orElseThrow(() -> new ResourceNotFound("Beneficiario", "id", id.toString()));

        System.out.println(beneficiario.getNombreapellido());
        String formTemplate = "src/main/resources/resources_files/2022_entrega_full.pdf";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PDDocument pdfDocument = PDDocument.load(new File(formTemplate))) {

            PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();
            List<PDField> fields = acroForm.getFields();
            System.out.println(fields.size() + " top-level fields were found on the form");

            for (PDField field : fields) {
                System.out.println(field.getFullyQualifiedName());
            }


            // get the document catalog
//            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

            // as there might not be an AcroForm entry a null check is necessary
            // Retrieve an individual field and set its value.
            PDTextField apellidos = (PDTextField) acroForm.getField("Nombre y apellidos del representante de la unidad familiar");
            PDTextField DNINIEPasaporte = (PDTextField) acroForm.getField("DNINIEPasaporte 1");
            PDTextField Telefono = (PDTextField) acroForm.getField("Teléfono");
            PDTextField Domicilio = (PDTextField) acroForm.getField("Domicilio");
            PDTextField Localidad = (PDTextField) acroForm.getField("Localidad");
            PDTextField cp = (PDTextField) acroForm.getField("CP");
            PDTextField NomOAR = (PDTextField) acroForm.getField("NomOAR");
            PDTextField DirOAR = (PDTextField) acroForm.getField("DirOAR");
            PDTextField numarAdra = (PDTextField) acroForm.getField("numarAdra");
            PDTextField miembrosUnidadFamiliar = (PDTextField) acroForm.getField("TOTAL MIEMBROS UNIDAD FAMILIAR");
            PDTextField ninos_02 = (PDTextField) acroForm.getField("Niños 02 ambos inclusive");
            PDTextField LocalidadAlta = (PDTextField) acroForm.getField("Localidad_alta");
            apellidos.setValue(beneficiario.getNombreapellido());
            DNINIEPasaporte.setValue(beneficiario.getDni());
            Domicilio.setValue(beneficiario.getDomicilio());
            Localidad.setValue(beneficiario.getCiudad());
            LocalidadAlta.setValue(beneficiario.getCiudad());
            cp.setValue("28850");
            NomOAR.setValue("Adra Torrejon");
            DirOAR.setValue("calle primavera 15");
            Telefono.setValue(String.valueOf(beneficiario.getTelefono()));
            numarAdra.setValue(String.valueOf(beneficiario.getNumeroadra()));
            miembrosUnidadFamiliar.setValue(String.valueOf(beneficiario.getFamiliares().size() + 1));
            ninos_02.setValue(String.valueOf(beneficiario.getFamiliares().stream().filter(familiar -> familiar.getEdad() < 3).count()));
            int index = 0;
            for (Alimento s : beneficiario.getAlimentos()) {
                index++;
                if (index == 1) {
                    acroForm.getField(String.format("dia_alta")).setValue(String.valueOf(s.getCreatedDate().atOffset(ZoneOffset.UTC).getDayOfMonth()));
                    acroForm.getField(String.format("mes_alta")).setValue(String.valueOf(s.getCreatedDate().atOffset(ZoneOffset.UTC).getMonth()));
                    acroForm.getField(String.format("ano_alta")).setValue(String.valueOf(s.getCreatedDate().atOffset(ZoneOffset.UTC).getYear()));

                }
                acroForm.getField(String.format("dia_%d", index)).setValue(String.valueOf(s.getCreatedDate().atOffset(ZoneOffset.UTC).getDayOfMonth()));
                acroForm.getField(String.format("mes_%d", index)).setValue(String.valueOf(s.getCreatedDate().atOffset(ZoneOffset.UTC).getMonthValue()));
                acroForm.getField(String.format("ano_%d", index)).setValue(String.valueOf(s.getCreatedDate().atOffset(ZoneOffset.UTC).getYear()));
                acroForm.getField(String.format("2022Arroz blanco_%d", index)).setValue(String.valueOf(s.getAlimento1()));
                acroForm.getField(String.format("2022Alubia cocida_%d", index)).setValue(String.valueOf(s.getAlimento2()));
                acroForm.getField(String.format("2022Conserva de atún_%d", index)).setValue(String.valueOf(s.getAlimento3()));
                acroForm.getField(String.format("2022Pasta alimenticia tipo macarrón_%d", index)).setValue(String.valueOf(s.getAlimento4()));
                acroForm.getField(String.format("2022Tomate frito en conserva_%d", index)).setValue(String.valueOf(s.getAlimento5()));
                acroForm.getField(String.format("2022Galletas_%d", index)).setValue(String.valueOf(s.getAlimento6()));
                acroForm.getField(String.format("2022Macedonia de verduras en conserva_%d", index)).setValue(String.valueOf(s.getAlimento7()));
                acroForm.getField(String.format("2022Fruta en conserva_%d", index)).setValue(String.valueOf(s.getAlimento8()));
                acroForm.getField(String.format("2022Cacao soluble_%d", index)).setValue(String.valueOf(s.getAlimento9()));
                acroForm.getField(String.format("2022Tarritos infantiles con pollo_%d", index)).setValue(String.valueOf(s.getAlimento10()));
                acroForm.getField(String.format("2022Tarritos infantiles de fruta_%d", index)).setValue(String.valueOf(s.getAlimento11()));
                acroForm.getField(String.format("2022Leche entera UHT_%d", index)).setValue(String.valueOf(s.getAlimento12()));
                acroForm.getField(String.format("2022Aceite de oliva_%d", index)).setValue(String.valueOf(s.getAlimento13()));
                System.out.println(s.getAlimento1());
            }


            // Save and close the filled out form.
//            pdfDocument.save("src/main/resources/resources_files/FillFormField.pdf");
            pdfDocument.save(out);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline;filename=das.pdf");
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
