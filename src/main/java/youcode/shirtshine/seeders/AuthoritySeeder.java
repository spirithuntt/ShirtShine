package youcode.shirtshine.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import youcode.shirtshine.domain.Authority;
import youcode.shirtshine.domain.enums.AuthorityEnum;
import youcode.shirtshine.repository.AuthorityRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthoritySeeder implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            seedAuthorities();
        }
    }

    public void seedAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        for (AuthorityEnum authorityName : AuthorityEnum.values()) {
            Authority authority = Authority.builder()
                    .name(authorityName)
                    .build();
            authorities.add(authority);
        }
        authorityRepository.saveAll(authorities);
    }
}
