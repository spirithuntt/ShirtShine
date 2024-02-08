package youcode.shirtshine.service;

import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Authority;
import youcode.shirtshine.domain.enums.AuthorityEnum;

import java.util.List;
import java.util.Optional;

@Component
public interface AuthorityService {
    List<Authority> getAllByName(List<AuthorityEnum> authorities);
    Optional<Authority> getByName(AuthorityEnum authorityEnum);
}

