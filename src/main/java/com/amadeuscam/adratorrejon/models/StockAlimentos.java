package com.amadeuscam.adratorrejon.models;

import java.time.Instant;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "StockAlimentos")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class StockAlimentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "ArrozBlanco", nullable = true)
    private int alimento1;
    @Column(name = "AlubiaCocida", nullable = true)
    private int alimento2;
    @Column(name = "ConservaAtun", nullable = true)
    private int alimento3;
    @Column(name = "PastaMacarron", nullable = true)
    private int alimento4;
    @Column(name = "TomateFrito", nullable = true)
    private int alimento5;
    @Column(name = "Galletas", nullable = true)
    private int alimento6;
    @Column(name = "MacedoniaVerduras", nullable = true)
    private int alimento7;
    @Column(name = "FrutaConserva", nullable = true)
    private int alimento8;
    @Column(name = "CacaoSoluble", nullable = true)
    private int alimento9;
    @Column(name = "TarritosPollo", nullable = true)
    private int alimento10;
    @Column(name = "TarritosFruta", nullable = true)
    private int alimento11;
    @Column(name = "LecheUHT", nullable = true)
    private int alimento12;
    @Column(name = "AceiteOliva", nullable = true)
    private int alimento13;

    @Column(name = "ArrozBlancoCaducidad", nullable = true)
    private LocalDate alimento1_cad;
    @Column(name = "AlubiaCocidaCaducidad", nullable = true)
    private LocalDate alimento2_cad;
    @Column(name = "ConservaAtunCaducidad", nullable = true)
    private LocalDate alimento3_cad;
    @Column(name = "PastaMacarronCaducidad", nullable = true)
    private LocalDate alimento4_cad;
    @Column(name = "TomateFritoCaducidad", nullable = true)
    private LocalDate alimento5_cad;
    @Column(name = "GalletasCaducidad", nullable = true)
    private LocalDate alimento6_cad;
    @Column(name = "MacedoniaVerdurasCaducidad", nullable = true)
    private LocalDate alimento7_cad;
    @Column(name = "FrutaConservaCaducidad", nullable = true)
    private LocalDate alimento8_cad;
    @Column(name = "CacaoSolubleCaducidad", nullable = true)
    private LocalDate alimento9_cad;
    @Column(name = "TarritosPolloCaducidad", nullable = true)
    private LocalDate alimento10_cad;
    @Column(name = "TarritosFrutaCaducidad", nullable = true)
    private LocalDate alimento11_cad;
    @Column(name = "LecheUHTCaducidad", nullable = true)
    private LocalDate alimento12_cad;
    @Column(name = "AceiteOlivaCaducidad", nullable = true)
    private LocalDate alimento13_cad;

    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

}