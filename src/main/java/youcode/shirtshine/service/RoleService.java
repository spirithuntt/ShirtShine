package youcode.shirtshine.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import youcode.shirtshine.domain.Authority;
import youcode.shirtshine.domain.Role;

@Component
public interface RoleService {

    Role save(Role role, boolean isSeed);

    Optional<Role> findDefaultRole();

    Role update(Role role, Long id);

    Optional<Role> getById(Long id);

    Optional<Role> getByName(String name);

    void delete(Long id);

    List<Role> getAll();

    Role grantAuthorities(List<Authority> authorities, Long id);

    Role revokeAuthorities(List<Authority> authorities, Long id);

}