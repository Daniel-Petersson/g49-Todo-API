package se.lexicon.g49todoapi.service;

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
                .map(person -> convertToDTOView(person))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTOView update(PersonDTOForm personDTOForm) {
        Person person = getPerson(personDTOForm.getId());
        return convertToDTOView(person);
    }

    @Override
    public void remove(Long id) {
        Person person = getPerson(id);
        personRepository.delete(person);
    }

    private Person getPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with the id: " + id));
    }

    private static PersonDTOView convertToDTOView(Person person) {
        return PersonDTOView.builder()
                .id(person.getId())
                .name(person.getName())
                .build();
    }
}
