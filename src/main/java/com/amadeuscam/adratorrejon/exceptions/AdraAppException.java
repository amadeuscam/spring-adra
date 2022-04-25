package com.amadeuscam.adratorrejon.exceptions;

import org.springframework.http.HttpStatus;

public class AdraAppException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus estado;
    private String message;

    public AdraAppException(HttpStatus estado, String message) {
        this.estado = estado;
        this.message = message;
    } public AdraAppException(HttpStatus estado, String message, String message1) {
        this.estado = estado;
        this.message = message;
        this.message = message1;
    }


    public HttpStatus getEstado() {
        return estado;
    }

    public void setEstado(HttpStatus estado) {
        this.estado = estado;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
