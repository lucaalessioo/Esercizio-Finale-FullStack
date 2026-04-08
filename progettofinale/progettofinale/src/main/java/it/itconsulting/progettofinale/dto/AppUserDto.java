package it.itconsulting.progettofinale.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppUserDto {
    
    @NotBlank(message = "L'username non può essere vuoto o con soli spazi")
    private String username;

    @NotBlank(message = "L'email non può essere vuoto o con soli spazi")
    private String email;
    
}
