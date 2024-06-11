package se.lexicon.g49todoapi.converter;

import se.lexicon.g49todoapi.domain.dto.RoleDTOView;
import se.lexicon.g49todoapi.entity.Role;

public interface RoleConverter {
    RoleDTOView toRoleDTO(Role entity);
    Role toRoleEntity(RoleDTOView dto);
}
