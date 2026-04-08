package it.itconsulting.progettofinale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.itconsulting.progettofinale.dto.TaskDto;
import it.itconsulting.progettofinale.model.AppUser;
import it.itconsulting.progettofinale.model.Task;
import it.itconsulting.progettofinale.repository.TaskRepository;


@Service 
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired 
    private AppUserService appUserService;

    public Task crea(TaskDto taskDto){
        if(taskDto!=null){
            Task taskDaCreare = converti(taskDto);
            taskRepository.save(taskDaCreare);
        }
        throw new IllegalArgumentException("Il task " + taskDto + " non è  valida");
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public Task getTask(long id){
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Il task con id " + id + " non trovato"));
    }

    

    public Task update (TaskDto taskDto, long id){
        Task taskDaModificare = getTask(id);

        if(taskDto!=null){
            taskDaModificare.setTitolo(taskDto.getTitolo());
            taskDaModificare.setDescrizione(taskDto.getDescrizione());
            taskDaModificare.setStato(taskDto.getStato());
            taskDaModificare.setDataTask(taskDto.getDataTask());
            taskDaModificare.setPriorita(taskDto.getPriorita());
            if(taskDto.getUser_id() != taskDaModificare.getAppUser().getId()){
                AppUser user = appUserService.getUtente(taskDto.getUser_id());
                taskDaModificare.setAppUser(user);
            }
            
            return taskRepository.save(taskDaModificare);
        }
         else{
            throw new IllegalArgumentException("Task " + taskDto + " non valida");
        }
    }

    public void delete (long id){
        taskRepository.deleteById(id);
    }
    
    
    private Task converti(TaskDto taskDto){
        Task t = new Task();
        t.setTitolo(taskDto.getTitolo());
        t.setDescrizione(taskDto.getDescrizione());
        t.setStato(taskDto.getStato());
        t.setPriorita(taskDto.getPriorita());
        t.setDataTask(taskDto.getDataTask());
        t.setAppUser(appUserService.getUtente(taskDto.getUser_id()));
        return t;
    }
}

