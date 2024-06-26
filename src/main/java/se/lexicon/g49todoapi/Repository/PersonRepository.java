package se.lexicon.g49todoapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.lexicon.g49todoapi.domain.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person  p WHERE size(p.tasks) = 0")
    List<Person> FindIdlePeople();

    @Query("SELECT p FROM Person p JOIN FETCH p.tasks WHERE p.id = :id")
    Optional<Person> findByIdWithTasks(@Param("id") Long id);

}


