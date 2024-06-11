package se.lexicon.g49todoapi.service;

import se.lexicon.g49todoapi.domain.dto.RoleDTOView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // This annotation is used to mark the class as a service provider
public interface RoleService {
    List<RoleDTOView> getAll();
}
