package se.lexicon.g49todoapi.domain.dto;

import lombok.*;

import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTOForm {
    private String email;
    private String password;
    private Set<RoleDTOForm> roles;
}
