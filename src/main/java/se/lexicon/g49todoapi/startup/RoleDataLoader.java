package se.lexicon.g49todoapi.startup;

import se.lexicon.g49todoapi.Repository.RoleRepository;
import se.lexicon.g49todoapi.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

     /* public RoleRepository getRoleRepository() {
        return roleRepository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }*/


    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("GUEST"));

    }
}
