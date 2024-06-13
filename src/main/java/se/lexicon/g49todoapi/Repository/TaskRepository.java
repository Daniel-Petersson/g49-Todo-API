package se.lexicon.g49todoapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.lexicon.g49todoapi.domain.entity.Task;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // select tasks contain title
    List<Task> findByTitleContainingIgnoreCase(String title);
    // select tasks by person's id
    List<Task> findByPersonId(Long id);
    // select tasks by status
    List<Task> findTaskByDone(boolean done);
    // select tasks by date between start and end
    List<Task> findByDeadlineBetween(LocalDate start, LocalDate end);
    // select all unassigned tasks
    List<Task> findByPersonIsNull();
    // select all unfinished tasks
    @Query("SELECT t FROM Task t WHERE t.done = false")
    List<Task> selectUnFinishedTasks();
    // select all unfinished and overdue tasks
    @Query("SELECT t FROM Task t WHERE t.done = false AND current_date > t.deadline")
    List<Task> selectUnFinishedAndOverdueTasks();
}
