package youcode.shirtshine.service;

import org.springframework.stereotype.Component;
import youcode.shirtshine.dto.request.AuthenticationRequest;
import youcode.shirtshine.dto.request.RegisterRequest;
import youcode.shirtshine.dto.response.AuthenticationResponse;


@Component
public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest user);

    AuthenticationResponse authenticate(AuthenticationRequest user);

}

