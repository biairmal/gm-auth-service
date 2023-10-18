package id.gudangmeme.authservice.authentication;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest dto);

    AuthenticationResponse login(LoginRequest dto);
}
