package se.lexicon.g49todoapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.g49todoapi.domain.dto.TaskDTOForm;
import se.lexicon.g49todoapi.domain.dto.TaskDTOView;
import se.lexicon.g49todoapi.service.TaskServiceImpl;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private TaskServiceImpl taskService;


    @GetMapping("{id}")
    public ResponseEntity<TaskDTOView> getTaskById(@PathVariable("id")Long id){
        TaskDTOView responseBody = taskService.findById(id);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<TaskDTOView>> getTaskByPersonId(@PathVariable("personId")Long personId){
        List<TaskDTOView> responseBody = taskService.findTasksByPersonId(personId);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("{startDate}/{endDate}")
    public ResponseEntity<List<TaskDTOView>> getTaskBetweenStartAndEndDate(@PathVariable("startDate")LocalDate startDate, @PathVariable("endDate")LocalDate endDate){
        List<TaskDTOView> responseBody = taskService.findTasksBetweenStartAndENdDate(startDate, endDate);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<TaskDTOView>> getAllUnassignedTodoItems(){
        List<TaskDTOView> responseBody = taskService.findAllUnassignedTodoItems();
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/unfinished")
    public ResponseEntity<List<TaskDTOView>> getAllUnfinishedTasksAndOverdue(){
        List<TaskDTOView> responseBody = taskService.findAllUnfinishedTasksAndOverdue();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/")
    public ResponseEntity<TaskDTOView> doRegister(TaskDTOForm taskDTOForm){
        TaskDTOView responseBody = taskService.create(taskDTOForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping("/")
    public ResponseEntity<TaskDTOView> doUpdate(TaskDTOForm taskDTOForm){
        TaskDTOView responseBody = taskService.update(taskDTOForm);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBody);
    }

    @DeleteMapping("{id}/remove")
    public ResponseEntity<Void> removeTask(@PathVariable("id") Long id){
        taskService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
