package converter;

import domain.dto.RoleDTOView;
import domain.entity.Role;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter{
    @Override
    public RoleDTOView toRoleDTO(Role entity) {
        RoleDTOView roleDTOView = new RoleDTOView(entity.getId(), entity.getName());
        return roleDTOView;
    }

    @Override
    public Role toRoleEntity(RoleDTOView dto) {
        return new Role(dto.getId(), dto.getName());
    }
}
