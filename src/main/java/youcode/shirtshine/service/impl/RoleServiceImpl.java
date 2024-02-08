package youcode.shirtshine.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import youcode.shirtshine.domain.Authority;
import youcode.shirtshine.domain.Role;
import youcode.shirtshine.domain.enums.AuthorityEnum;
import youcode.shirtshine.handler.request.CustomException;
import youcode.shirtshine.repository.RoleRepository;
import youcode.shirtshine.service.AuthorityService;
import youcode.shirtshine.service.RoleService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final AuthorityService authorityService;
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll(){
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("VIEW_ROLES"))
            return roleRepository.findAll();
        else return null;
    }

    @Override
    public Role save(Role role, boolean isSeed) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!isSeed && authentication != null) {
            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            if (!authorities.contains("CREATE_ROLE")) {
                throw new CustomException("Insufficient authorities", HttpStatus.UNAUTHORIZED);
            }
        }

        if (findDefaultRole().isPresent() && role.isDefault()) {
            throw new CustomException("There is already a default role", HttpStatus.UNAUTHORIZED);
        }
        return roleRepository.save(role);
    }
    @Override
    public Optional<Role> findDefaultRole(){
        return roleRepository.findByIsDefaultTrue();
    }

    @Override
    public Role grantAuthorities(List<Authority> authoritiesToGrant, Long id){
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("GRANT_AUTHORITY_TO_ROLE")){
            Role role = roleRepository.findById(id).orElse(null);
            if (role != null){
                Set<Authority> newAuthorities = new HashSet<>(role.getAuthorities());
                newAuthorities.addAll(authorityService.getAllByName(
                        authoritiesToGrant.stream()
                                .map(authority -> authority.getName())
                                .collect(Collectors.toList())
                ));                List<Authority> authorityList = new ArrayList<>(newAuthorities);
                role.setAuthorities(authorityList);
                return roleRepository.save(role);
            }
            return null;
        }
        return null;
    }

    @Override
    public Role revokeAuthorities(List<Authority> authoritiesToRevoke, Long id){
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("REVOKE_AUTHORITY_FROM_ROLE")){
            Role role = roleRepository.findById(id).orElse(null);
            if (role != null){
                List<Authority> currentAuthorities = role.getAuthorities();
                currentAuthorities.removeAll(authorityService.getAllByName(
                        authoritiesToRevoke.stream()
                                .map(authority -> authority.getName())
                                .collect(Collectors.toList())
                ));                role.setAuthorities(currentAuthorities);
                return roleRepository.save(role);
            }
            return null;
        }return null;
    }

    @Override
    public Role update(Role role, Long id){
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("UPDATE_ROLE")){
            Role existingRole = getById(id).orElse(null);
            if (existingRole != null){
                existingRole.setName(role.getName());
                existingRole.setAuthorities(role.getAuthorities());
                if (role.isDefault() && findDefaultRole().isPresent()) throw new CustomException("There is already a default role", HttpStatus.UNAUTHORIZED);
                existingRole.setDefault(role.isDefault());
                return roleRepository.save(existingRole);
            }
            return null;
        }return null;
    }

    @Override
    public Optional<Role> getById(Long id){
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> getByName(String name){
        return roleRepository.findByName(name);
    }

    @Override
    public void delete(Long id){
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("DELETE_ROLE"))getById(id).ifPresent(roleRepository::delete);
    }

}

