package se.lexicon.g49todoapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.Repository.PersonRepository;
import se.lexicon.g49todoapi.Repository.UserRepository;
import se.lexicon.g49todoapi.converter.RoleConverter;
import se.lexicon.g49todoapi.domain.dto.*;
import se.lexicon.g49todoapi.domain.entity.Person;
import se.lexicon.g49todoapi.domain.entity.Role;
import se.lexicon.g49todoapi.domain.entity.Task;
import se.lexicon.g49todoapi.domain.entity.User;
import se.lexicon.g49todoapi.exeption.DataNotFoundException;

import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final RoleConverter roleConverter;



    @Override
    @Transactional
    public PersonDTOView create(PersonDTOForm personDTOForm) {
        //Validate input
        if (personDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");
        //Validate person doesn't all ready exist

        User user = userRepository.findById(personDTOForm.getEmail()).orElseThrow(()-> new DataNotFoundException("not found"));

        //Create person
        Person person = Person.builder()
                .name(personDTOForm.getName())
                .user(user)
                .build();
        //Save person
        Person savedPerson = personRepository.save(person);

        System.out.println(savedPerson);
        //convert and return person to dto
        return roleConverter.toPersonDTOView(savedPerson);
    }

    @Override
    public PersonDTOView findById(Long id) {
        Person person = getPerson(id);
        return convertToDTOView(person);
    }

    @Override
    public List<PersonDTOView> findAll() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .map(this::convertToDTOView)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PersonDTOView update(PersonDTOForm personDTOForm) {
        if (personDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");
        Person existingPerson = personRepository.findById(personDTOForm.getId())
                .orElseThrow(() -> new IllegalArgumentException("Person with the id: " + personDTOForm.getId() + " not found"));
        existingPerson.setName(personDTOForm.getName());
        existingPerson.setUser(User.builder().email(existingPerson.getUser().getEmail())
                .roles(existingPerson.getUser().getRoles().stream()
                        .map(role -> Role.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .build()).collect(Collectors.toSet())).build());
        Person updatedPerson = personRepository.save(existingPerson);
        return convertToDTOView(updatedPerson);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Person person = getPerson(id);
        personRepository.delete(person);
    }

    private Person getPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with the id: " + id));
    }
private PersonDTOView convertToDTOView(Person person) {
    List<Task> tasks = person.getTasks();
    List<TaskDTOView> taskDTOViews = tasks.stream()
            .map(roleConverter::toTaskDTOView)
            .collect(Collectors.toList());

    return PersonDTOView.builder()
            .id(person.getId())
            .name(person.getName())
            .user(UserDTOView.builder()
                    .email(person.getUser().getEmail())
                    .roles(person.getUser().getRoles().stream()
                            .map(role -> RoleDTOView.builder()
                                    .id(role.getId())
                                    .name(role.getName())
                                    .build())
                            .collect(Collectors.toSet()))
                    .build())
            .tasks(taskDTOViews)
            .build();
}

}
