package it.itconsulting.progettofinale.dto;

import java.time.LocalDate;

import it.itconsulting.progettofinale.model.Task;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TaskDto {
    
    @NotBlank(message = "Il titolo non può essere vuoto o con soli spazi")
    private String titolo;
    
    @NotBlank(message = "La descrizione non può essere vuota o con soli spazi")
    private String descrizione;
    
    @NotNull(message = "Lo stato non può essere vuoto o con soli spazi")
    private Task.Stato stato;

    @NotNull(message = "La priorità non può essere vuota o con soli spazi")
    private Task.Priorita priorita;

    @Positive(message = "L'id non può assumere valori negativi")
    @NotBlank(message = "L'id non può essere vuoto o con soli spazi")
    private long user_id;

    @FutureOrPresent(message = "La data del task  può essere precedente")
    @NotNull(message = "La data del task non può essere vuota o con solo spazi")
    private LocalDate dataTask;


}
