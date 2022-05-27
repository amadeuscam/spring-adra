package com.amadeuscam.adratorrejon.controller;

import com.amadeuscam.adratorrejon.repository.BeneficiarioRepository;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class MicrosoftFilesController {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;


    @GetMapping("/valoracion-social/{id}")
    public ResponseEntity<?> generateHojaDeEntrega(@PathVariable(value = "id") UUID id) throws Exception {
//        String inputfilepath = "src/main/resources/resources_files/vl.docx";
//        String outputfilepath = "src/main/resources/resources_files/OUT_VariableReplace.docx";
//
//        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
//                .load(new java.io.File(inputfilepath));
//        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
//        VariablePrepare.prepare(wordMLPackage);
//        System.out.println(documentPart.getContent());
//
//        HashMap<String, String> mappings = new HashMap<String, String>();
//        mappings.put("parentesco", "hijo");
//        mappings.put("numar_adra", "1");
//
//        // Approach 1 (from 3.0.0; faster if you haven't yet caused unmarshalling to occur):
//
//        documentPart.variableReplace(mappings);
//        Docx4J.save(wordMLPackage, new File(outputfilepath));

        Docx docx = new Docx("src/main/resources/resources_files/vl.docx");
        docx.setVariablePattern(new VariablePattern("<<", ">>"));

// preparing variables
        Variables variables = new Variables();
        variables.addTextVariable(new TextVariable("<<numar_adra>>", "1"));
        variables.addTextVariable(new TextVariable("<<parentesco>>", "hijo"));

// fill template
        docx.fillTemplate(variables);

// save filled .docx file
        docx.save("src/main/resources/resources_files/lstypka.docx");


        return null;
    }

}
