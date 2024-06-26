package se.lexicon.g49todoapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.g49todoapi.domain.dto.PersonDTOForm;
import se.lexicon.g49todoapi.domain.dto.PersonDTOView;
import se.lexicon.g49todoapi.service.PersonService;
import se.lexicon.g49todoapi.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private PersonService personService;
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<PersonDTOView>> getAllPersons(){
        List<PersonDTOView> persons = personService.findAll();
        for (PersonDTOView person : persons){
            System.out.println(person.toString());
        }
        return ResponseEntity.ok(persons);
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDTOView> getPersonById(@PathVariable("id") Long id){
        PersonDTOView responseBody = personService.findById(id);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/")
    public ResponseEntity<PersonDTOView> doRegister(@RequestBody PersonDTOForm personDTOForm){
       PersonDTOView responseBody = personService.create(personDTOForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping(("/"))
    public ResponseEntity<PersonDTOView> doUpdate(@RequestBody PersonDTOForm personDTOForm){
        PersonDTOView responseBody = personService.update(personDTOForm);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBody);
    }

    @DeleteMapping("{id}/remove")
    public ResponseEntity<Void> removePerson(@PathVariable("id") Long id){
        personService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
