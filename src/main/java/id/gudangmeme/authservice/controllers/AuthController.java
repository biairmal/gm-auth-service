package id.gudangmeme.authservice.controllers;

import id.gudangmeme.authservice.authentication.AuthenticationService;
import id.gudangmeme.authservice.authentication.dto.LoginRequest;
import id.gudangmeme.authservice.authentication.dto.RegisterRequest;
import id.gudangmeme.authservice.security.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest dto) {
        var result = authenticationService.register(dto);

        return ResponseEntity.ok(result);
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest dto) {
        var result = authenticationService.login(dto);

        return ResponseEntity.ok(result);
    }

    @GetMapping("refresh-token")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX))
            return ResponseEntity.status(401).build();

        String token = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
        var result = authenticationService.refreshToken(token);
        if (result == null) return ResponseEntity.status(401).build();

        return ResponseEntity.ok(result);
    }

}
