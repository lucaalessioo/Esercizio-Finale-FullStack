package it.itconsulting.progettofinale.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskDto {
    
    @NotBlank(message = "Il titolo non può essere vuoto o con soli spazi")
    private String titolo;
    
    @NotBlank(message = "La descrizione non può essere vuota o con soli spazi")
    private String descrizione;
    
    @NotBlank(message = "L'indirizzo non può essere vuoto o con soli spazi")
    private int user_id;

    @FutureOrPresent(message = "La data del task  può essere precedente")
    @NotNull(message = "La data del task non può essere vuota o con solo spazi")
    private LocalDate dataTask;
}
