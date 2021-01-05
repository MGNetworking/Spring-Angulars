package com.spring.security.jwt.springsecurityjwt.web;

import com.spring.security.jwt.springsecurityjwt.dao.TaskRepository;
import com.spring.security.jwt.springsecurityjwt.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskWebController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> taskList(){
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task save(@RequestBody Task task){

        System.out.println("******** tache ajouter ********* ");
        System.out.println(task);

        return taskRepository.save(task);
    }

}
