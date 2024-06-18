package se.lexicon.g49todoapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.domain.dto.TaskDTOForm;
import se.lexicon.g49todoapi.domain.dto.TaskDTOView;


import java.time.LocalDate;
import java.util.List;

@Service
public interface TaskService {
    //todo create
    TaskDTOView create(TaskDTOForm taskDTOForm);
    // findById
    TaskDTOView findById(Long id);
    // update
    TaskDTOView update(TaskDTOForm taskDTOForm);
    // delete
    void remove(Long id);
    // findTasksByPersonId
    List<TaskDTOView>  findTasksByPersonId(Long personId);
    // findTasksBetweenStartAndEndDate
    List<TaskDTOView> findTasksBetweenStartAndENdDate(LocalDate start, LocalDate end);
    // findAllUnassignedTasks
    List<TaskDTOView> findAllUnassignedTodoItems();
    // findAllUnfinishedTasksAndOverdue
    List<TaskDTOView> findAllUnfinishedTasksAndOverdue();
}
