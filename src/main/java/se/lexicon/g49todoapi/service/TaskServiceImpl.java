package se.lexicon.g49todoapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.Repository.TaskRepository;
import se.lexicon.g49todoapi.domain.dto.TaskDTOForm;
import se.lexicon.g49todoapi.domain.dto.TaskDTOView;
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
    @Transactional
    public TaskDTOView create(TaskDTOForm taskDTOForm) {
        if (taskDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");
        //Optional<Task> existingTask = taskRepository.findById(taskDTOForm.getId());
        //if (existingTask.isPresent())throw new IllegalArgumentException("Task already exist");
        Task task = Task.builder()
                .title(taskDTOForm.getTitle())
                .description(taskDTOForm.getDescription())
                .deadline(taskDTOForm.getDeadline())
                .done(taskDTOForm.isDone())
                .build();
        Task savedTask = taskRepository.save(task);

        return convertToDTOView(savedTask);
    }

    @Override
    public TaskDTOView findById(Long id) {
        Task task = getTask(id);

        return convertToDTOView(task);
    }

    @Override
    @Transactional
    public TaskDTOView update(TaskDTOForm taskDTOForm) {
        if (taskDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");
        Task existingTask = taskRepository.findById(taskDTOForm.getId())
                .orElseThrow(() -> new IllegalArgumentException("Task with the id: " + taskDTOForm.getId() + " not found"));
        existingTask.setTitle(taskDTOForm.getTitle());
        existingTask.setDescription(taskDTOForm.getDescription());
        existingTask.setDeadline(taskDTOForm.getDeadline());
        existingTask.setDone(taskDTOForm.isDone());
        Task updatedTask = taskRepository.save(existingTask);
        return convertToDTOView(updatedTask);
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
                .build();
    }
}
