package com.amadeuscam.adratorrejon.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTAuthResponseDTO {
    private String token;


    public JWTAuthResponseDTO(String token) {
        this.token = token;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
