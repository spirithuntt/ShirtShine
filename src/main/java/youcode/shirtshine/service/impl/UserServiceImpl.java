package youcode.shirtshine.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import youcode.shirtshine.domain.Role;
import youcode.shirtshine.domain.User;
import youcode.shirtshine.exceptionHandler.OperationException;
import youcode.shirtshine.repository.RoleRepository;
import youcode.shirtshine.repository.UserRepository;
import youcode.shirtshine.service.UserService;
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
            throw new OperationException("Role or user not found");
        }throw new OperationException("You are not authorized to perform this action");
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            return userRepository.findByEmail(username).orElse(null);
        }

        return null;
    }


}
