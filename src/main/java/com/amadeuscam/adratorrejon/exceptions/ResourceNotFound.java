package com.amadeuscam.adratorrejon.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
@Setter
public class ResourceNotFound extends RuntimeException {

    private String nombreDelRecurso;
    private String nombreDelCampo;
    private String valorDelCampo;

    public ResourceNotFound(String nombreDelRecurso, String nombreDelCampo, String valorDelCampo) {
        super(String.format("%s no encontrada con: %s : '%s'", nombreDelRecurso, nombreDelCampo, valorDelCampo));
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelCampo = valorDelCampo;
    }


}
