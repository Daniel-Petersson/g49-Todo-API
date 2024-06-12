package se.lexicon.g49todoapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.domain.dto.TaskDTOForm;
import se.lexicon.g49todoapi.domain.dto.TaskDtoView;


import java.time.LocalDate;
import java.util.List;

@Service
public interface TaskService {
    //todo create
    TaskDtoView create(TaskDTOForm taskDTOForm);
    // findById
    TaskDtoView findById(Long id);
    // update
    TaskDtoView update(TaskDTOForm taskDTOForm);
    // delete
    void remove(Long id);
    // findTasksByPersonId
    List<TaskDtoView>  findTasksByPersonId(Long personId);
    // findTasksBetweenStartAndEndDate
    List<TaskDtoView> findTasksBetweenStartAndENdDate(LocalDate start, LocalDate end);
    // findAllUnassignedTasks
    List<TaskDtoView> findAllUnassignedTodoItems();
    // findAllUnfinishedTasksAndOverdue
    List<TaskDtoView> findAllUnfinishedTasksAndOverdue();
}
