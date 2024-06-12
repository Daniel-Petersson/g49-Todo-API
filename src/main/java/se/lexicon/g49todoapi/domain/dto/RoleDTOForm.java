package se.lexicon.g49todoapi.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTOForm {
    // todo : add validation annotations if needed

    private Long id;
    private String name;
}
