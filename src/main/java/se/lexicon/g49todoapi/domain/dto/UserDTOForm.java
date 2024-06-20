package se.lexicon.g49todoapi.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTOForm {

    private String email;
    private String password;

    private Set<RoleDTOForm> roles;


}
