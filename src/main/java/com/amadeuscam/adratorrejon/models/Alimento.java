package com.amadeuscam.adratorrejon.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;


@Table(name = "alimentos")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "ArrozBlanco", nullable = false)
    private int alimento1;
    @Column(name = "GarbanzosCocidos", nullable = false)
    private int alimento2;
    @Column(name = "ConservaAtún", nullable = false)
    private int alimento3;
    @Column(name = "PastaEspagueti", nullable = false)
    private int alimento4;
    @Column(name = "TomateFrito", nullable = false)
    private int alimento5;
    @Column(name = "Galletas", nullable = false)
    private int alimento6;
    @Column(name = "MacedoniaVerduras", nullable = false)
    private int alimento7;
    @Column(name = "CacaoSoluble", nullable = false)
    private int alimento8;
    @Column(name = "TarritosPollo", nullable = false)
    private int alimento9;
    @Column(name = "TarritosFruta", nullable = false)
    private int alimento10;
    @Column(name = "LecheUHT", nullable = false)
    private int alimento11;
    @Column(name = "AceiteOliva", nullable = false)
    private int alimento12;
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @JoinColumn(name = "beneficiarios_id")
    private Beneficiario beneficiario;

}
