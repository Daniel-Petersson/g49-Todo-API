package se.lexicon.g49todoapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.Repository.PersonRepository;
import se.lexicon.g49todoapi.Repository.TaskRepository;
import se.lexicon.g49todoapi.domain.dto.TaskDTOForm;
import se.lexicon.g49todoapi.domain.dto.TaskDtoView;
import se.lexicon.g49todoapi.domain.entity.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDtoView create(TaskDTOForm taskDTOForm) {
        if (taskDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");
        Optional<Task> existingTask = taskRepository.findById(taskDTOForm.getId());
        if (existingTask.isPresent())throw new IllegalArgumentException("Task already exist");
        Task task = Task.builder()
                .title(taskDTOForm.getTitle())
                .description(taskDTOForm.getDescription())
                .deadline(taskDTOForm.getDeadline())
                .done(taskDTOForm.isDone())
                .build();
        Task savedTask = taskRepository.save(task);

        return getBuild(savedTask);
    }

    @Override
    public TaskDtoView findById(Long id) {
        Task task = getTask(id);

        return getBuild(task);
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

    @Override
    @Transactional
    public TaskDtoView update(TaskDTOForm taskDTOForm) {
        Task task = getTask(taskDTOForm.getId());
        return getBuild(task);
    }

    @Override
    public void remove(Long id) {
        Task task = getTask(id);
        taskRepository.delete(task);

    }

    @Override
    public List<TaskDtoView> findTasksByPersonId(Long personId) {
        List<Task> tasks = taskRepository.findByPersonId(personId);
        return getTaskDtoViews(tasks);
    }

    @Override
    public List<TaskDtoView> findTasksBetweenStartAndENdDate(LocalDate start, LocalDate end) {
        List<Task> tasks = taskRepository.findByDeadlineBetween(start, end);
        return getTaskDtoViews(tasks);
    }

    @Override
    public List<TaskDtoView> findAllUnassignedTodoItems() {
        List<Task> tasks = taskRepository.selectUnFinishedTasks();
        return getTaskDtoViews(tasks);
    }

    @Override
    public List<TaskDtoView> findAllUnfinishedTasksAndOverdue() {
        List<Task> tasks = taskRepository.selectUnFinishedAndOverdueTasks();
        return getTaskDtoViews(tasks);
    }

    private static List<TaskDtoView> getTaskDtoViews(List<Task> tasks) {
        List<TaskDtoView> taskDtoViews = new ArrayList<>();
        for (Task entity : tasks){
            taskDtoViews.add(getBuild(entity));
        }
        return taskDtoViews;
    }


    private static TaskDtoView getBuild(Task task) {
        return TaskDtoView.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .done(task.isDone())
                .build();
    }
}
