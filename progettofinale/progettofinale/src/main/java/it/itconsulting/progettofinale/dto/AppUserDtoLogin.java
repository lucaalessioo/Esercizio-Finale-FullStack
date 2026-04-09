package it.itconsulting.progettofinale.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppUserDtoLogin {
    

    @NotBlank(message = "L'email non può essere vuoto o con soli spazi")
    private String email;

    @NotBlank(message = "La password non può essere vuoto o con soli spazi")
    private String password;
    
}
