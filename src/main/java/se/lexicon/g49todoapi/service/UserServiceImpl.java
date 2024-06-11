package se.lexicon.g49todoapi.service;

import se.lexicon.g49todoapi.domain.dto.UserDTOForm;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    //todo: add required dependencies
    //todo: impl methods
    @Override
    public UserDTOView register(UserDTOForm userDTOForm) {
        return null;
    }

    @Override
    public UserDTOView getByEmail(String email) {
        return null;
    }

    @Override
    public void disableByEmail(String email) {

    }

    @Override
    public void enableByEmail(String email) {

    }
}
