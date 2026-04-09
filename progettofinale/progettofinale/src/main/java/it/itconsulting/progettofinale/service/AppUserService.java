package it.itconsulting.progettofinale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.itconsulting.progettofinale.dto.AppUserDto;
import it.itconsulting.progettofinale.dto.AppUserDtoLogin;
import it.itconsulting.progettofinale.model.AppUser;
import it.itconsulting.progettofinale.repository.AppUserRepository;

@Service
public class AppUserService{
    
    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser create(AppUserDto appUserDto){

        if(appUserDto==null){
            throw new IllegalArgumentException("L'utente " + appUserDto + " non è valido");
            
        }

        if(appUserRepository.findByEmail(appUserDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email gia presente non è stato possibile creare l account");
        } 

        return appUserRepository.save(converti(appUserDto));
    }


    public AppUser login(AppUserDtoLogin appUserDto) {
        AppUser appUser = appUserRepository.findByEmail(appUserDto.getEmail());
        System.out.println(appUser);

        if(appUserDto.getPassword().equals(appUser.getPassword())) {
            return appUser;
        } else {
            
            throw new IllegalArgumentException("Credenziali errate");
        }

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
        a.setPassword(appUserDto.getPassword());

        return a;
    }

}
