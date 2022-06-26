package com.amadeuscam.adratorrejon.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.xmlbeans.XmlException;

// @Service
// @Transactional
public interface MicrosoftFilesService {
    public ByteArrayOutputStream generateExcelBeneficarios();
    public ByteArrayOutputStream generateHojaDeValoracion(UUID id) throws XmlException, IOException;
}
