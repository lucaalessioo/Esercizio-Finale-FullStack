package it.itconsulting.progettofinale.controller;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.itconsulting.progettofinale.dto.AppUserDto;
import it.itconsulting.progettofinale.dto.AppUserDtoLogin;
import it.itconsulting.progettofinale.model.AppUser;
import it.itconsulting.progettofinale.model.Errore;
import it.itconsulting.progettofinale.service.AppUserService;

@RestController
@RequestMapping("api")
@CrossOrigin(origins ={"http://127.0.0.1:5500", "http://localhost:8080"})
public class AppUserController {
    
    @Autowired
    private AppUserService appUserService;

    
     @PostMapping("login")
    public ResponseEntity<Object> create(@RequestBody(required = false) @Validated AppUserDtoLogin appUserDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Errore errore = new Errore();
            errore.setMessaggio(bindingResult.getAllErrors().stream().map(obError -> obError.getDefaultMessage()).collect(Collectors.joining(",")));
            errore.setDataErrore(LocalDateTime.now());
            return ResponseEntity.badRequest().body(errore);
        }

        try{
            AppUser nuovaPersona = appUserService.login(appUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuovaPersona);
        } catch(IllegalArgumentException e) {
            Errore errore = new Errore();
            errore.setMessaggio(e.getMessage());
            errore.setDataErrore(LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errore);
        }
    }
    
    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody(required = false) @Validated AppUserDto appUserDto,BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Errore errore = new Errore();
            errore.setMessaggio(bindingResult.getAllErrors().stream().map(obError -> obError.getDefaultMessage()).collect(Collectors.joining(",")));
            errore.setDataErrore(LocalDateTime.now());

            return ResponseEntity.badRequest().body(errore);
        }

        try{
            
            AppUser appUser = appUserService.create(appUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(appUser);
            
        } catch(IllegalArgumentException e) {
            Errore errore = new Errore();
            errore.setMessaggio(e.getMessage());
            errore.setDataErrore(LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errore);
        }
    }
    

}
