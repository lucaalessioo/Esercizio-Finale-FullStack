package it.itconsulting.progettofinale.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.itconsulting.progettofinale.dto.TaskDto;
import it.itconsulting.progettofinale.model.Errore;
import it.itconsulting.progettofinale.model.Task;
import it.itconsulting.progettofinale.service.TaskService;


@RestController
@RequestMapping("api")
@CrossOrigin(origins ={"http://127.0.0.1:5500", "http://localhost:8080"})
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @PostMapping("tasks")
    public ResponseEntity<Object> create(@RequestBody(required = false) @Validated TaskDto taskDto, BindingResult bindingResult) {

            if(bindingResult.hasErrors()) {
                Errore errore = new Errore();
                errore.setMessaggio(bindingResult.getAllErrors().stream().map(obError -> obError.getDefaultMessage()).collect(Collectors.joining(",")));
                errore.setDataErrore(LocalDateTime.now());

                return ResponseEntity.badRequest().body(errore);
            }

            try {

                Task nuovaTask = taskService.crea(taskDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuovaTask);

            } catch (IllegalArgumentException e) {
                Errore errore = new Errore();
                errore.setMessaggio(e.getMessage());
                errore.setDataErrore(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errore);
            }
    }

    //     @GetMapping("tasks")
    //     public List<Task> getAll() {
    //         return taskService.getAll();
    // }
    
        @GetMapping("tasks")
        public List<Task> getAll(@RequestParam String stato, @RequestParam String priorita, @RequestParam long id) {
            return taskService.getAll(stato,priorita,id);
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<Object> getTask(@PathVariable Long id) {
        Task casa = null;

        try {
            casa = taskService.getTask(id);
            return ResponseEntity.ok().body(casa);
        } catch (IllegalArgumentException e) {
            Errore errore = new Errore();
            errore.setMessaggio(e.getMessage());
            errore.setDataErrore(LocalDateTime.now());

            return ResponseEntity.badRequest().body(errore);
        }
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<Object> update(@RequestBody(required = false) @Validated TaskDto taskDto, BindingResult bindingResult, @PathVariable Long id) {
        
        if(bindingResult.hasErrors()) {
            Errore errore = new Errore();
            errore.setMessaggio(bindingResult.getAllErrors().stream().map(obError -> obError.getDefaultMessage()).collect(Collectors.joining(",")));
            errore.setDataErrore(LocalDateTime.now());

            return ResponseEntity.badRequest().body(errore);
        }

        Task t = null;
        try {

            t = taskService.update(taskDto, id);
            return ResponseEntity.ok().body(t);

        } catch (IllegalArgumentException e) {
            Errore errore = new Errore();
            errore.setMessaggio(e.getMessage());
            errore.setDataErrore(LocalDateTime.now());

            return ResponseEntity.badRequest().body(errore);
        }

    }

        @DeleteMapping("tasks/{id}")
            public void delete(@PathVariable Long id) {
            taskService.delete(id);
    }

}
