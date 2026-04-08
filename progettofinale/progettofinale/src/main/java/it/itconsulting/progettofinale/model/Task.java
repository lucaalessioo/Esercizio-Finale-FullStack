package it.itconsulting.progettofinale.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue
    private int id;

    public enum Stato {
    IN_CORSO,
    PROGRAMMATO,
    TERMINATO
    }

    private String titolo;
    private String descrizione;
    private Stato stato; 

    @Column(name = "local_date")
    private LocalDate dataTask;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    @JsonIgnore
    private int user_id;
}
