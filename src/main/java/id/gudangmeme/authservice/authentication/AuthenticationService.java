package id.gudangmeme.authservice.authentication;

import id.gudangmeme.authservice.authentication.dto.AuthenticationResponse;
import id.gudangmeme.authservice.authentication.dto.LoginRequest;
import id.gudangmeme.authservice.authentication.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest dto);

    AuthenticationResponse login(LoginRequest dto);

    AuthenticationResponse refreshToken(String token);
}
