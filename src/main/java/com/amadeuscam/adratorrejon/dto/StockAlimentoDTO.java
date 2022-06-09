package com.amadeuscam.adratorrejon.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
@Getter
@Setter
public class StockAlimentoDTO {
    private String id;
    @NotNull
    private int alimento1;
    @NotNull
    private int alimento2;
    @NotNull
    private int alimento3;
    @NotNull
    private int alimento4;
    @NotNull
    private int alimento5;
    @NotNull
    private int alimento6;
    @NotNull
    private int alimento7;
    @NotNull
    private int alimento8;
    @NotNull
    private int alimento9;
    @NotNull
    private int alimento10;
    @NotNull
    private int alimento11;
    @NotNull
    private int alimento12;
    @NotNull
    private int alimento13;
    private LocalDate alimento1_cad;
    private LocalDate alimento2_cad;
    private LocalDate alimento3_cad;
    private LocalDate alimento4_cad;
    private LocalDate alimento5_cad;
    private LocalDate alimento6_cad;
    private LocalDate alimento7_cad;
    private LocalDate alimento8_cad;
    private LocalDate alimento9_cad;
    private LocalDate alimento10_cad;
    private LocalDate alimento11_cad;
    private LocalDate alimento12_cad;
    private LocalDate alimento13_cad;
}