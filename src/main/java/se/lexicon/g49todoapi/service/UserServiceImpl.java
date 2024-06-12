package se.lexicon.g49todoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.lexicon.g49todoapi.Repository.RoleRepository;
import se.lexicon.g49todoapi.Repository.UserRepository;
import se.lexicon.g49todoapi.domain.dto.UserDTOForm;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;
import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.domain.entity.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    //todo: add required dependencies
    //todo: impl methods
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }




    @Override
    public UserDTOView register(UserDTOForm dtoForm) {
        //input validation
        if (dtoForm == null) throw new IllegalArgumentException("Form cannot be empty");
        //Existing user validation
       boolean existingEmail = userRepository.existsByEmail(dtoForm.getEmail());{
            if (existingEmail) throw new IllegalArgumentException("Email already exists");
        }
        //crate user entity
        User user = new User(dtoForm.getEmail(), dtoForm.getPassword());
        //save user entity
        userRepository.save(user);
        //convert to dto and return

        return UserDTOView.builder().build();
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
