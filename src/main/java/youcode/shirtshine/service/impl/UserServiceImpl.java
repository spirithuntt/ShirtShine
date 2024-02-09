package youcode.shirtshine.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import youcode.shirtshine.domain.Role;
import youcode.shirtshine.domain.User;
import youcode.shirtshine.repository.RoleRepository;
import youcode.shirtshine.repository.UserRepository;
import youcode.shirtshine.service.UserService;
import youcode.shirtshine.handler.request.CustomException;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Role grantRoleToUser(Long userId, Long roleId) {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("ASSIGN_ROLE_TO_USER")) {
            Role role = roleRepository.findById(roleId).orElse(null);
            User user = userRepository.findById(userId).orElse(null);
            if (role != null && user != null) {
                user.setRole(role);
                userRepository.save(user);
                return role;
            }
            throw new CustomException("Role or user not found", HttpStatus.NOT_FOUND);
        }throw new CustomException("Insufficient authorities", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }


}
