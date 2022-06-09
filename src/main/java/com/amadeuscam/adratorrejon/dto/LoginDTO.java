package com.amadeuscam.adratorrejon.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;

   public String getUsernameOrEmail() {
       return usernameOrEmail;
   }

   public void setUsernameOrEmail(String usernameOrEmail) {
       this.usernameOrEmail = usernameOrEmail;
   }

   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }
}
