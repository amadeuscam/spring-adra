package com.amadeuscam.adratorrejon.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "familiares", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombreapellido"})})
@Getter
@Setter
public class Familiar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombreapellido", nullable = false)
    private String nombreapellido;
    @Column(name = "parentesco", nullable = false)
    private String parentesco;
    @Column(name = "sexo", nullable = false)
    private String sexo;
    @Column(name = "dni")
    private String dni;
    @Column(name = "otros_documentos")
    private String otros_documentos;
    @Column(name = "fechanacimiento", nullable = false)
    private LocalDate fechanacimiento;
    @Transient // cu acest decorador esta columna no se salva en base de datos
    private Integer edad;
    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiario_id", nullable = false)
    @JsonBackReference
    private Beneficiario beneficiario;

    public Integer getEdad() {
        return Period.between(this.fechanacimiento, LocalDate.now()).getYears();
    }
}
