package se.lexicon.g49todoapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import se.lexicon.g49todoapi.Repository.RoleRepository;
import se.lexicon.g49todoapi.Repository.UserRepository;
import se.lexicon.g49todoapi.domain.dto.RoleDTOView;
import se.lexicon.g49todoapi.domain.dto.UserDTOForm;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;
import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.domain.entity.Role;
import se.lexicon.g49todoapi.domain.entity.User;
import se.lexicon.g49todoapi.exeption.DataDuplicateException;
import se.lexicon.g49todoapi.exeption.DataNotFoundException;
import se.lexicon.g49todoapi.util.CustomPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, CustomPasswordEncoder customPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.customPasswordEncoder = customPasswordEncoder;
    }


    @Override
    @Transactional
    public UserDTOView register(UserDTOForm userDTOForm) {
        //input validation
        if (userDTOForm == null) throw new IllegalArgumentException("Form cannot be empty");
        //Existing user validation
        boolean existingEmail = userRepository.existsByEmail(userDTOForm.getEmail());
        if (existingEmail) throw new DataDuplicateException("Email already exists");

        //Validate roles in db.
        Set<Role> roles = userDTOForm.getRoles()
                .stream()
                .map(roleDTOForm -> roleRepository.findById(roleDTOForm.getId())
                        .orElseThrow(() -> new DataNotFoundException("Role not found")))
                .collect(Collectors.toSet());
        //Convert UserDtoForm to entity
        User user = User.builder()
                .email(userDTOForm.getEmail())
                //Hash the password
                .password(customPasswordEncoder.encode(userDTOForm.getPassword()))
                .roles(roles)
                .build();

        //save user entity
        User savedUser = userRepository.save(user);
        //convert entity to dto and return
        Set<RoleDTOView> roleDTOViews = savedUser.getRoles()
                .stream()
                .map(
                        role -> RoleDTOView.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .build())
                .collect(Collectors.toSet());
        return UserDTOView.builder()
                .email(savedUser.getEmail())
                .roles(roleDTOViews)
                .build();
    }

    @Override
    public UserDTOView getByEmail(String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new DataNotFoundException("Email does not exist"));
        Set<RoleDTOView> roleDTOViews = user.getRoles()
                .stream()
                .map(
                        role -> RoleDTOView.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .build()
                ).collect(Collectors.toSet());
        return UserDTOView.builder()
                .email(user.getEmail())
                .roles(roleDTOViews)
                .build();
    }

    @Override
    public void disableByEmail(String email) {
        isEmailExisting(email);
        userRepository.updateExpiredByEmail(email,true);
    }

    @Override
    public void enableByEmail(String email) {
        isEmailExisting(email);
        userRepository.updateExpiredByEmail(email,false);
    }

    private void isEmailExisting(String email) {
        if (!userRepository.existsByEmail(email)) throw new DataNotFoundException("Email does not exist");
    }


}
