package com.amadeuscam.adratorrejon.models;


import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "beneficiarios")
@Getter
@Setter
@NoArgsConstructor
public class Beneficiario {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "id", updatable = false, nullable = false,columnDefinition = "CHAR(36)")
//    @Type(type="uuid-char")
    private UUID id ;

    @Column(name = "nombreapellido", nullable = false, unique = true)
    private String nombreapellido;
    @Column(name = "dni")
    private String dni;
    @Column(name = "otrosdocumentos")
    private String otrosdocumentos;
    @Column(name = "fechanacimiento", nullable = false)
    private LocalDate fechanacimiento;
    @Transient // cu acest decorador esta columna no se salva en base de datos
    private Integer edad;
    @Column(name = "numeroadra", nullable = false, unique = true)
    private Integer numeroadra;
    @Column(name = "nacionalidad", nullable = false)
    private String nacionalidad;
    @Column(name = "covid", nullable = false)
    private boolean covid;
    @Column(name = "domicilio", nullable = false)
    private String domicilio;
    @Column(name = "ciudad", nullable = false)
    private String ciudad;
    @Column(name = "areacte", nullable = false)
    private boolean areacte;
    @Column(name = "telefono", nullable = false)
    private int telefono;
    @Column(name = "email", nullable = true)
    private String email;
    @Column(name = "mensaje", nullable = true)
    private String mensaje;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "sexo", nullable = false)
    private String sexo;
    @Column(name = "discapacidad", nullable = false)
    private boolean discapacidad;
    @Column(name = "categoria", nullable = false)
    private String categoria;
    @Column(name = "empadronamiento", nullable = false)
    private boolean empadronamiento;
    @Column(name = "librofamilia", nullable = false)
    private boolean librofamilia;
    @Column(name = "fotocopiadni", nullable = false)
    private boolean fotocopiadni;
    @Column(name = "prestaciones", nullable = false)
    private boolean prestaciones;
    @Column(name = "nomnia", nullable = false)
    private boolean nomnia;
    @Column(name = "certnegativo", nullable = false)
    private boolean certnegativo;
    @Column(name = "aquilerhipoteca", nullable = false)
    private boolean aquilerhipoteca;
    @Column(name = "recibos", nullable = false)
    private boolean recibos;


    @OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Familiar> familiares = new HashSet<>();

    @OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Alimento> alimentos = new HashSet<>();

    public Beneficiario(UUID id, String nombreapellido, String dni, String otrosdocumentos, LocalDate fechanacimiento, Integer edad, Integer numeroadra, String nacionalidad, boolean covid, String domicilio, String ciudad, boolean areacte, int telefono, String email, String mensaje, boolean active, String sexo, boolean discapacidad, String categoria, boolean empadronamiento, boolean librofamilia, boolean fotocopiadni, boolean prestaciones, boolean nomnia, boolean certnegativo, boolean aquilerhipoteca, boolean recibos) {
        this.id = id;
        this.nombreapellido = nombreapellido;
        this.dni = dni;
        this.otrosdocumentos = otrosdocumentos;
        this.fechanacimiento = fechanacimiento;
        this.edad = edad;
        this.numeroadra = numeroadra;
        this.nacionalidad = nacionalidad;
        this.covid = covid;
        this.domicilio = domicilio;
        this.ciudad = ciudad;
        this.areacte = areacte;
        this.telefono = telefono;
        this.email = email;
        this.mensaje = mensaje;
        this.active = active;
        this.sexo = sexo;
        this.discapacidad = discapacidad;
        this.categoria = categoria;
        this.empadronamiento = empadronamiento;
        this.librofamilia = librofamilia;
        this.fotocopiadni = fotocopiadni;
        this.prestaciones = prestaciones;
        this.nomnia = nomnia;
        this.certnegativo = certnegativo;
        this.aquilerhipoteca = aquilerhipoteca;
        this.recibos = recibos;
    }

    public Integer getEdad() {
        return Period.between(this.fechanacimiento, LocalDate.now()).getYears();
    }

//    public UUID getId() {
//        return id;
//    }
}
