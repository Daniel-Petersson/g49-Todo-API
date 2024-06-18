package se.lexicon.g49todoapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.lexicon.g49todoapi.Repository.TaskRepository;
import se.lexicon.g49todoapi.domain.dto.TaskDTOForm;
import se.lexicon.g49todoapi.domain.entity.Task;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  @Test
void createTaskWithValidInput() {
    TaskDTOForm taskDTOForm = new TaskDTOForm();
    taskDTOForm.setTitle("Task one");
    taskDTOForm.setDescription("do some thing");
    taskDTOForm.setDeadline(LocalDate.of(2024, 7, 8));
    taskDTOForm.setDone(false);

    // Assertions to verify that the fields are not empty
    assertNotNull(taskDTOForm.getTitle());
    assertFalse(taskDTOForm.getTitle().isEmpty());
    assertNotNull(taskDTOForm.getDescription());
    assertFalse(taskDTOForm.getDescription().isEmpty());
    assertNotNull(taskDTOForm.getDeadline());
    assertNotNull(taskDTOForm.isDone());

    Task task = new Task();
    task.setTitle(taskDTOForm.getTitle());
    task.setDescription(taskDTOForm.getDescription());
    task.setDeadline(taskDTOForm.getDeadline());
    task.setDone(taskDTOForm.isDone());

    when(taskRepository.save(any(Task.class))).thenReturn(task);

    assertNotNull(taskService.create(taskDTOForm));
    verify(taskRepository, times(1)).save(any(Task.class));
}

    @Test
    void createTaskWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> taskService.create(null));
        verify(taskRepository, times(0)).save(any(Task.class));
    }


}