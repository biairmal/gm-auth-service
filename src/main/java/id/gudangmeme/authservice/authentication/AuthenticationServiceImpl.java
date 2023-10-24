package id.gudangmeme.authservice.authentication;

import id.gudangmeme.authservice.authentication.dto.AuthenticationResponse;
import id.gudangmeme.authservice.authentication.dto.LoginRequest;
import id.gudangmeme.authservice.authentication.dto.RegisterRequest;
import id.gudangmeme.authservice.security.jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Autowired
    AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
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
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        UserAccount user = userService.findByUsername(dto.getUsername());
        Token token = tokenService.generateUserToken(user);

        return AuthenticationResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(String token) {
        String username = jwtUtil.extractUsername(token);
        if (username == null) return null;

        UserAccount user = userService.findByUsername(username);
        Token newToken = tokenService.generateUserToken(user);

        return AuthenticationResponse.builder()
                .accessToken(newToken.getAccessToken())
                .refreshToken(newToken.getRefreshToken())
                .build();
    }

}
