package se.lexicon.g49todoapi.domain.dto;

import lombok.*;
import se.lexicon.g49todoapi.domain.entity.Person;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTOForm {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private Long assignedPerson;
}