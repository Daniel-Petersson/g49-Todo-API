package se.lexicon.g49todoapi.converter;


import se.lexicon.g49todoapi.domain.dto.PersonDTOView;
import se.lexicon.g49todoapi.domain.dto.RoleDTOView;

import se.lexicon.g49todoapi.domain.dto.TaskDTOView;
import se.lexicon.g49todoapi.domain.entity.Person;
import se.lexicon.g49todoapi.domain.entity.Role;
import se.lexicon.g49todoapi.domain.entity.Task;


public interface RoleConverter {
    RoleDTOView toRoleDTO(Role entity);
    Role toRoleEntity(RoleDTOView dto);
    TaskDTOView toTaskDTOView(Task task);
    PersonDTOView toPersonDTOView(Person person);
}
