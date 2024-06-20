package se.lexicon.g49todoapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.Repository.PersonRepository;
import se.lexicon.g49todoapi.Repository.TaskRepository;
import se.lexicon.g49todoapi.domain.dto.PersonDTOView;
import se.lexicon.g49todoapi.domain.dto.TaskDTOForm;
import se.lexicon.g49todoapi.domain.dto.TaskDTOView;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;
import se.lexicon.g49todoapi.domain.entity.Person;
import se.lexicon.g49todoapi.domain.entity.Task;
import se.lexicon.g49todoapi.exeption.DataNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;


    @Override
    @Transactional
    public TaskDTOView create(TaskDTOForm taskDTOForm) {
        if (taskDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");

        if (taskDTOForm.getAssignedPerson() == null) {
            throw new IllegalArgumentException("Assigned person cannot be null");
        }
        Person person = personRepository.findById(taskDTOForm.getAssignedPerson()).orElseThrow(()->new DataNotFoundException("some error"));
        Task task = Task.builder()
                .title(taskDTOForm.getTitle())
                .description(taskDTOForm.getDescription())
                .deadline(taskDTOForm.getDeadline())
                .done(taskDTOForm.isDone())
                .person(person)
                .build();
        Task savedTask = taskRepository.save(task);

        return convertToDTOView(savedTask);
    }

    @Override
    public TaskDTOView findById(Long id) {
        Task task = getTask(id);

        return convertToDTOView(task);
    }
    //Todo
    @Override
    @Transactional
    public TaskDTOView update(TaskDTOForm taskDTOForm) {
        /*if (taskDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");
        Task existingTask = taskRepository.findById(taskDTOForm.getId())
                .orElseThrow(() -> new IllegalArgumentException("Task with the id: " + taskDTOForm.getId() + " not found"));
        Person person = personRepository.findById(taskDTOForm.getPerson().getId()).orElseThrow(()-> new IllegalArgumentException("Person with the id: "+ taskDTOForm.getPerson().getId() + " not found"));
        existingTask.setTitle(taskDTOForm.getTitle());
        existingTask.setDescription(taskDTOForm.getDescription());
        existingTask.setDeadline(taskDTOForm.getDeadline());
        existingTask.setDone(taskDTOForm.isDone());
        existingTask.setPerson(person);
        Task updatedTask = taskRepository.save(existingTask);
        return convertToDTOView(updatedTask);*/
        return null;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Task task = getTask(id);
        taskRepository.delete(task);

    }

    @Override
    public List<TaskDTOView> findTasksByPersonId(Long personId) {
        List<Task> tasks = taskRepository.findByPersonId(personId);
        return convertToDTOViewList(tasks);
    }

    @Override
    public List<TaskDTOView> findTasksBetweenStartAndENdDate(LocalDate start, LocalDate end) {
        List<Task> tasks = taskRepository.findByDeadlineBetween(start, end);
        return convertToDTOViewList(tasks);
    }

    @Override
    public List<TaskDTOView> findAllUnassignedTodoItems() {
        List<Task> tasks = taskRepository.selectUnFinishedTasks();
        return convertToDTOViewList(tasks);
    }

    @Override
    public List<TaskDTOView> findAllUnfinishedTasksAndOverdue() {
        List<Task> tasks = taskRepository.selectUnFinishedAndOverdueTasks();
        return convertToDTOViewList(tasks);
    }

    private List<TaskDTOView> convertToDTOViewList(List<Task> tasks) {
        List<TaskDTOView> taskDTOViews = new ArrayList<>();
        for (Task entity : tasks){
            taskDTOViews.add(convertToDTOView(entity));
        }
        return taskDTOViews;
    }


    private Task getTask(Long id) {
        Task task;
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            task = taskOptional.get();
        } else {
            throw new IllegalArgumentException("Task with the id: " + id + " not found");
        }
        return task;
    }

    private TaskDTOView convertToDTOView(Task task) {
        return TaskDTOView.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .done(task.isDone())
                .person(PersonDTOView.builder()
                        .id(task.getId())
                        .name(task.getPerson().getName())
                        .user(UserDTOView.builder()
                                .email(task.getPerson().getUser().getEmail())
                                .build())
                        .build())
                .build();
    }
}
