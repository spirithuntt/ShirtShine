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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        //? admin roles
        Authority manageOrders = authorityService.getByName(AuthorityEnum.MANAGE_ORDERS)
                .orElseThrow(() -> new RuntimeException("MANAGE_ORDERS authority not found"));
        Authority manageProducts = authorityService.getByName(AuthorityEnum.MANAGE_PRODUCTS)
                .orElseThrow(() -> new RuntimeException("MANAGE_PRODUCTS authority not found"));


        //? Create roles and associate authorities
        Role userRole = Role.builder()
                .name("USER")
                .isDefault(true)
                .build();

        Role adminRole = Role.builder()
                .name("ADMIN")
                .authorities(Arrays.asList(viewRoles, manageOrders, manageProducts))
                .build();

        Role superAdminRole = Role.builder()
                .name("SUPER_ADMIN")
                .authorities(getAllAuthorities())
                .build();

        roleService.save(userRole, true);
        roleService.save(adminRole, true);
        roleService.save(superAdminRole, true);
    }

    private List<Authority> getAllAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        for (AuthorityEnum authorityEnum : AuthorityEnum.values()) {
            Authority authority = authorityService.getByName(authorityEnum)
                    .orElseThrow(() -> new RuntimeException(authorityEnum + " authority not found"));
            authorities.add(authority);
        }
        return authorities;
    }

}
