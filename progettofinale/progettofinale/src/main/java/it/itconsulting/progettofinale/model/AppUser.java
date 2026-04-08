package it.itconsulting.progettofinale.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "tasks")
public class AppUser {
    @Id
    @GeneratedValue
    private int id;

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user_id")
    private List<Task> tasks;
}
