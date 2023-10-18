package id.gudangmeme.authservice.authentication;

import id.gudangmeme.authservice.security.jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Autowired
    AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest dto) {
        UserAccount user = userService.create(dto);
        String token = jwtUtil.createToken(user, null);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(null)
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        var user = userService.findByUsername(dto.getEmail());
        var token = jwtUtil.createToken(user, null);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(null)
                .build();
    }
}
