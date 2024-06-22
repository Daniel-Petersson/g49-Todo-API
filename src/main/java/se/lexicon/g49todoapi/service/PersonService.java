package se.lexicon.g49todoapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.domain.dto.PersonDTOForm;
import se.lexicon.g49todoapi.domain.dto.PersonDTOView;

import java.util.List;

@Service
public interface PersonService {
    // create
    PersonDTOView create(PersonDTOForm personDTOForm);
    // findById
    PersonDTOView findById(Long id);

    // findAll
    List<PersonDTOView> findAll();
    // update
    PersonDTOView update(PersonDTOForm personDTOForm);
    // delete
    void remove(Long id);
}
