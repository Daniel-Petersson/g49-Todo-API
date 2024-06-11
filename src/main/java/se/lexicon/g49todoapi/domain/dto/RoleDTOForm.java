package se.lexicon.g49todoapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTOForm {
    // todo : add validation annotations if needed

    private Long id;
    private String name;
}
