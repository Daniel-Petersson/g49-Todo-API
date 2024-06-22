package se.lexicon.g49todoapi.converter;

import se.lexicon.g49todoapi.domain.dto.PersonDTOView;
import se.lexicon.g49todoapi.domain.dto.RoleDTOView;

import se.lexicon.g49todoapi.domain.dto.TaskDTOView;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;
import se.lexicon.g49todoapi.domain.entity.Person;
import se.lexicon.g49todoapi.domain.entity.Role;
import org.springframework.stereotype.Component;
import se.lexicon.g49todoapi.domain.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class RoleConverterImpl implements RoleConverter {
    @Override
    public RoleDTOView toRoleDTO(Role entity) {
        //Using builder annotation
        return RoleDTOView.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
        //return new RoleDTOView(entity.getId(), entity.getName());

    }

    @Override
    public Role toRoleEntity(RoleDTOView dto) {
        //Using builder annotation
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
        //return new Role(dto.getId(), dto.getName());
    }

public TaskDTOView toTaskDTOView(Task task) {
    return TaskDTOView.builder()
            .id(task.getId())
            .title(task.getTitle())
            .description(task.getDescription())
            .deadline(task.getDeadline())
            .done(task.isDone())
            .person(PersonDTOView.builder()
                    .id(task.getPerson().getId())
                    .name(task.getPerson().getName())
                    .build())
            .build();
}

  @Override
public PersonDTOView toPersonDTOView(Person person) {
    if (person == null) {
        return null;
    }

    List<TaskDTOView> taskDTOViews = new ArrayList<>();
    if (person.getTasks() != null){
        for (Task task : person.getTasks()) {
            taskDTOViews.add(toTaskDTOView(task));
        }
    }


    return PersonDTOView.builder()
            .id(person.getId())
            .name(person.getName())
            .user(UserDTOView.builder()
                    .email(person.getUser().getEmail())
                    .roles(person.getUser().getRoles().stream()
                            .map(role -> RoleDTOView.builder()
                                    .id(role.getId())
                                    .name(role.getName())
                                    .build())
                            .collect(Collectors.toSet())).build())
            .tasks(taskDTOViews)
            .build();
}


}
