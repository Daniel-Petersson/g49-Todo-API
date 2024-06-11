package se.lexicon.g49todoapi.service;

import se.lexicon.g49todoapi.domain.dto.UserDTOForm;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;

public interface UserService {
    // Method to register a new user and return the view representation of the user
    UserDTOView register(UserDTOForm userDTOForm);

    // Method to retrieve a user by their email and return the view representation of the user
    UserDTOView getByEmail(String email);

    // Method to disable a user's account by their email
    void disableByEmail(String email);

    // Method to enable a user's account by their email
    void enableByEmail(String email);
}
