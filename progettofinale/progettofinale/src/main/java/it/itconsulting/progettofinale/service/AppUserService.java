package it.itconsulting.progettofinale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.itconsulting.progettofinale.dto.AppUserDto;
import it.itconsulting.progettofinale.model.AppUser;
import it.itconsulting.progettofinale.repository.AppUserRepository;

@Service
public class AppUserService{
    
    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser create(AppUserDto appUserDto){
        if(appUserDto!=null){
            appUserRepository.save(converti(appUserDto));
        }
        throw new IllegalArgumentException("L'utente " + appUserDto + " non è valido");
    }

    public List<AppUser> getAll(){
        return appUserRepository.findAll();
    }

    public AppUser getUtente(long id){
        return appUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Persona con id " + id + " non trovata"));
    }

    public AppUser update (AppUserDto appUserDto, long id){
        AppUser utenteDaModificare = getUtente(id);

        if(appUserDto!=null){
            utenteDaModificare.setUsername(appUserDto.getUsername());
            utenteDaModificare.setEmail(appUserDto.getEmail());
            
            return appUserRepository.save(utenteDaModificare);
        }
        throw new IllegalArgumentException("Utente " + appUserDto + " non valido");
    }

    public void delete (long id){
       appUserRepository.deleteById(id);
    }

    private AppUser converti(AppUserDto appUserDto){
        AppUser a = new AppUser();
        a.setUsername(appUserDto.getUsername());
        a.setEmail(appUserDto.getEmail());

        return a;
    }

}
