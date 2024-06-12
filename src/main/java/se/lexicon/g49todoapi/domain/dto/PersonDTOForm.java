package se.lexicon.g49todoapi.domain.dto;

import lombok.*;
import se.lexicon.g49todoapi.domain.entity.Task;
import se.lexicon.g49todoapi.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDTOForm {
    private Long id;
    private String name;
    private UserDTOForm user;
    private List<TaskDTOForm> tasks = new ArrayList<>();
}
