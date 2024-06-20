package se.lexicon.g49todoapi.controller;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.g49todoapi.domain.dto.UserDTOForm;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;
import se.lexicon.g49todoapi.service.UserServiceImpl;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserServiceImpl userService;


    @GetMapping("{email}")
    public ResponseEntity<UserDTOView> getUserByEmail(@PathVariable("email")String email) {
        UserDTOView userDTOView = userService.getByEmail(email);
        return ResponseEntity.ok(userDTOView);
    }

    @PostMapping("/")
    public ResponseEntity<UserDTOView> doRegister(@RequestBody @Valid UserDTOForm userDTOForm) { //validation in DTOForm
        UserDTOView responseBody = userService.register(userDTOForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }


    @PutMapping("/{email}/enable")
    public ResponseEntity<Void> enableUserByEmail(@PathVariable("email") String email) {
        userService.enableByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{email}/disable")
    public ResponseEntity<Void> disableUserByEmail(@PathVariable("email") String email) {
        userService.disableByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
