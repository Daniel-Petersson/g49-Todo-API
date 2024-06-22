package se.lexicon.g49todoapi.domain.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTOView {
    private Long id;
    private String name;
    private UserDTOView user;
    private List<TaskDTOView> tasks = new ArrayList<>();
}
