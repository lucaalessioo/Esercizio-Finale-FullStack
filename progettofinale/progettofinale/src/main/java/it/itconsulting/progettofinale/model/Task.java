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
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "appUser")
public class Task {
    @Id
    @GeneratedValue
    private long id;

    public enum Stato {
    IN_CORSO,
    PROGRAMMATO,
    TERMINATO
    }

    public enum Priorita{
        BASSA,
        MEDIA,
        ALTA
    }

    private String titolo;
    private String descrizione;
    private Stato stato; 
    private Priorita priorita;

    @Column(name = "local_date")
    private LocalDate dataTask;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    @JsonIgnore
    private AppUser appUser;
}
