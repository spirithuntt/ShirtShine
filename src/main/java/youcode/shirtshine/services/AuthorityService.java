package youcode.shirtshine.services;

import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Authority;

import java.util.List;

@Component
public interface AuthorityService {
    List<Authority> getAllByName(List<String> authorities);
}

