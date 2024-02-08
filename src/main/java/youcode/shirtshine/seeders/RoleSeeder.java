package youcode.shirtshine.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Authority;
import youcode.shirtshine.domain.Role;
import youcode.shirtshine.domain.enums.AuthorityEnum;
import youcode.shirtshine.repository.RoleRepository;
import youcode.shirtshine.service.AuthorityService;
import youcode.shirtshine.service.RoleService;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            seedRoles();
        }
    }

    private void seedRoles() {
        //? create authorities
        Authority viewRoles = authorityService.getByName(AuthorityEnum.VIEW_ROLES)
                .orElseThrow(() -> new RuntimeException("VIEW_ROLES authority not found"));

        Authority createRole = authorityService.getByName(AuthorityEnum.CREATE_ROLE)
                .orElseThrow(() -> new RuntimeException("CREATE_ROLE authority not found"));

        Authority viewUsers = authorityService.getByName(AuthorityEnum.VIEW_USERS)
                .orElseThrow(() -> new RuntimeException("VIEW_USERS authority not found"));


        //? Create roles and associate authorities
        Role userRole = Role.builder()
                .name("USER")
                .isDefault(true)
                .build();

        Role adminRole = Role.builder()
                .name("ADMIN")
                .authorities(Arrays.asList(viewRoles, viewUsers))
                .build();

        Role superAdminRole = Role.builder()
                .name("SUPER_ADMIN")
                .authorities(Arrays.asList(viewRoles, createRole))
                .build();

        roleService.save(userRole, true);
        roleService.save(adminRole, true);
        roleService.save(superAdminRole, true);
    }

}
