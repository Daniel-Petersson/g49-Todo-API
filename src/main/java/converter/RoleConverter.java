package converter;

import domain.dto.RoleDTOView;
import domain.entity.Role;

public interface RoleConverter {
    RoleDTOView toRoleDTO(Role entity);
    Role toRoleEntity(RoleDTOView dto);
}
