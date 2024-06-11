package startup;

import Repository.RoleRepository;
import domain.entity.Role;
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
        System.out.println("#########################");
        // execute this logic...
        // How to call save method of RoleRepository Interface?
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("GUEST"));
        // add more roles as needed
    }
}
