package se.lexicon.g49todoapi.converter;

import se.lexicon.g49todoapi.domain.dto.RoleDTOView;
import se.lexicon.g49todoapi.domain.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter{
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
}
