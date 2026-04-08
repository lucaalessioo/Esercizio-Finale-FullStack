package it.itconsulting.progettofinale.model;


@Data
@Entity
public class AppUser {
    @Id
    @GeneratoredValue
    private int id;

    private String username;
    private String password;
    private String email;
}
