package com.amadeuscam.adratorrejon.dto;

import com.amadeuscam.adratorrejon.models.Alimento;
import com.amadeuscam.adratorrejon.models.Familiar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
public class BeneficiarioDTO {

    private Long id;
    @NotEmpty
    private String nombreapellido;
    private String dni;
    private String otrosdocumentos;
    @NotNull
    private LocalDate fechanacimiento;
    @NotNull
    private Integer numeroadra;
    @NotEmpty
    private String nacionalidad;

    private boolean covid;
    @NotEmpty
    private String domicilio;
    @NotEmpty
    private String ciudad;
    @NotNull
    private boolean areacte;
    @NotNull
    private int telefono;

    private String email;
    private String mensaje;

    @NotNull
    private boolean active;
    @NotEmpty
    private String sexo;
    @NotNull
    private boolean discapacidad;
    @NotEmpty
    private String categoria;

    private boolean empadronamiento;

    private boolean librofamilia;

    private boolean fotocopiadni;

    private boolean prestaciones;

    private boolean nomnia;

    private boolean certnegativo;

    private boolean aquilerhipoteca;

    private boolean recibos;
    @JsonIgnore
    private boolean tienebaby;

    private Integer edad;

    private Set<Familiar> familiares;
    private Set<Alimento> alimentos;

    public BeneficiarioDTO() {
    }

    public boolean isTienebaby() {
        return this.familiares.stream().anyMatch(familiar -> familiar.getEdad() < 3);
    }

    public void setTienebaby(boolean tienebaby) {
        this.tienebaby = tienebaby;
    }
}
