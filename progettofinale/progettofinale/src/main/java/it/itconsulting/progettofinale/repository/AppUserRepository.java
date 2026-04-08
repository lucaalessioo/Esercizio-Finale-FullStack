package it.itconsulting.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.itconsulting.progettofinale.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
    
}
