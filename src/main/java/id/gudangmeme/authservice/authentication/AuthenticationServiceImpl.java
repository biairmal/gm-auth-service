package id.gudangmeme.authservice.authentication;

import id.gudangmeme.authservice.authentication.dto.AuthenticationResponse;
import id.gudangmeme.authservice.authentication.dto.LoginRequest;
import id.gudangmeme.authservice.authentication.dto.RegisterRequest;
import id.gudangmeme.authservice.token.Token;
import id.gudangmeme.authservice.token.TokenService;
import id.gudangmeme.authservice.user.UserAccount;
import id.gudangmeme.authservice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest dto) {
        UserAccount user = userService.create(dto);
        Token token = tokenService.generateUserToken(user);

        return AuthenticationResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        UserAccount user = userService.findByUsername(dto.getEmail());
        Token token = tokenService.generateUserToken(user);

        return AuthenticationResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

}
