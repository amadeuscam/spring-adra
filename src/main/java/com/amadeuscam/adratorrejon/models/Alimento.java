package com.amadeuscam.adratorrejon.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;


@Table(name = "alimentos")
@NoArgsConstructor
@Entity
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "ArrozBlanco", nullable = false)
    private int alimento1;
    @Column(name = "AlubiaCocida", nullable = false)
    private int alimento2;
    @Column(name = "ConservaAtun", nullable = false)
    private int alimento3;
    @Column(name = "PastaMacarron", nullable = false)
    private int alimento4;
    @Column(name = "TomateFrito", nullable = false)
    private int alimento5;
    @Column(name = "Galletas", nullable = false)
    private int alimento6;
    @Column(name = "MacedoniaVerduras", nullable = false)
    private int alimento7;
    @Column(name = "FrutaConserva", nullable = false)
    private int alimento8;
    @Column(name = "CacaoSoluble", nullable = false)
    private int alimento9;
    @Column(name = "TarritosPollo", nullable = false)
    private int alimento10;
    @Column(name = "TarritosFruta", nullable = false)
    private int alimento11;
    @Column(name = "LecheUHT", nullable = false)
    private int alimento12;
    @Column(name = "AceiteOliva", nullable = false)
    private int alimento13;
    @Column(name = "signature", nullable = true)
    @Lob
    private String signature;
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @JoinColumn(name = "beneficiarios_id")
    private Beneficiario beneficiario;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAlimento1() {
        return alimento1;
    }

    public void setAlimento1(int alimento1) {
        this.alimento1 = alimento1;
    }

    public int getAlimento2() {
        return alimento2;
    }

    public void setAlimento2(int alimento2) {
        this.alimento2 = alimento2;
    }

    public int getAlimento3() {
        return alimento3;
    }

    public void setAlimento3(int alimento3) {
        this.alimento3 = alimento3;
    }

    public int getAlimento4() {
        return alimento4;
    }

    public void setAlimento4(int alimento4) {
        this.alimento4 = alimento4;
    }

    public int getAlimento5() {
        return alimento5;
    }

    public void setAlimento5(int alimento5) {
        this.alimento5 = alimento5;
    }

    public int getAlimento6() {
        return alimento6;
    }

    public void setAlimento6(int alimento6) {
        this.alimento6 = alimento6;
    }

    public int getAlimento7() {
        return alimento7;
    }

    public void setAlimento7(int alimento7) {
        this.alimento7 = alimento7;
    }

    public int getAlimento8() {
        return alimento8;
    }

    public void setAlimento8(int alimento8) {
        this.alimento8 = alimento8;
    }

    public int getAlimento9() {
        return alimento9;
    }

    public void setAlimento9(int alimento9) {
        this.alimento9 = alimento9;
    }

    public int getAlimento10() {
        return alimento10;
    }

    public void setAlimento10(int alimento10) {
        this.alimento10 = alimento10;
    }

    public int getAlimento11() {
        return alimento11;
    }

    public void setAlimento11(int alimento11) {
        this.alimento11 = alimento11;
    }

    public int getAlimento12() {
        return alimento12;
    }

    public void setAlimento12(int alimento12) {
        this.alimento12 = alimento12;
    }

    public int getAlimento13() {
        return alimento13;
    }

    public void setAlimento13(int alimento13) {
        this.alimento13 = alimento13;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }
}
