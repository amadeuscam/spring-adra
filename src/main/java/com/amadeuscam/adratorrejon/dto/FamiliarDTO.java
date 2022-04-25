package com.amadeuscam.adratorrejon.dto;

import com.amadeuscam.adratorrejon.models.Beneficiario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class FamiliarDTO {


    private long id;
    @NotEmpty
    private String nombreapellido;
    @NotEmpty
    private String parentesco;
    @NotEmpty
    private String sexo;
    private String dni;
    private String otros_documentos;
    @NotNull
    private LocalDate fechanacimiento;
    private Integer edad;
    @NotNull
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreapellido() {
        return nombreapellido;
    }

    public void setNombreapellido(String nombreapellido) {
        this.nombreapellido = nombreapellido;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getOtros_documentos() {
        return otros_documentos;
    }

    public void setOtros_documentos(String otros_documentos) {
        this.otros_documentos = otros_documentos;
    }

    public LocalDate getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
