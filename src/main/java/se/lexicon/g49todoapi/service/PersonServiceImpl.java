package se.lexicon.g49todoapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.Repository.PersonRepository;
import se.lexicon.g49todoapi.domain.dto.PersonDTOForm;
import se.lexicon.g49todoapi.domain.dto.PersonDTOView;
import se.lexicon.g49todoapi.domain.entity.Person;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;


    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    @Transactional
    public PersonDTOView create(PersonDTOForm personDTOForm) {
        //Validate input
        if (personDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");
        //Validate person doesn't all ready exist
        Optional<Person> existingPerson = personRepository.findById(personDTOForm.getId());
        if (existingPerson.isPresent()) throw new IllegalArgumentException("Person already exist");
        //Create person
        Person person = Person.builder()
                .name(personDTOForm.getName())
                .build();
        //Save person
        Person savedPerson = personRepository.save(person);
        //convert and return person to dto
        return convertToDTOView(savedPerson);
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
        return PersonDTOView.builder()
                .id(person.getId())
                .name(person.getName())
                .build();
    }
}
