package se.lexicon.g49todoapi.converter;

import se.lexicon.g49todoapi.domain.dto.RoleDTOView;
import se.lexicon.g49todoapi.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter{
    @Override
    public RoleDTOView toRoleDTO(Role entity) {
        return new RoleDTOView(entity.getId(), entity.getName());

    }

    @Override
    public Role toRoleEntity(RoleDTOView dto) {
        return new Role(dto.getId(), dto.getName());
    }
}
