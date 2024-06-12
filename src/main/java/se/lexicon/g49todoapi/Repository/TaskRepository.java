package se.lexicon.g49todoapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.g49todoapi.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    //todo: select tasks contain title
    // select tasks by person's id
    // select tasks by status
    // select tasks by date between start and end
    // select all unassigned tasks
    // select all unfinished tasks
    // select all unfinished and overdue tasks

}
