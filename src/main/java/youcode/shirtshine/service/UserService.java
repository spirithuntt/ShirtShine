package youcode.shirtshine.service;

import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Role;
import youcode.shirtshine.domain.User;
import youcode.shirtshine.dto.request.GrantRoleToUserRequestDto;

import java.util.Optional;

@Component
public interface UserService {
    Optional<User> getById(Long id);

    Role grantRoleToUser(Long userId, Long roleId);
}
