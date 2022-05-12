package com.amadeuscam.adratorrejon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlimentoDTO {
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
    private String signature;
}
