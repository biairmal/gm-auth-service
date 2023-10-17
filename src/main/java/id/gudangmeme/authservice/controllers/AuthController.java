package id.gudangmeme.authservice.controllers;

import id.gudangmeme.authservice.dto.in.LoginDto;
import id.gudangmeme.authservice.dto.in.RegisterDto;
import id.gudangmeme.authservice.security.JwtUtil;
import id.gudangmeme.authservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto dto) {
        var result = userService.create(dto);

        return ResponseEntity.ok(result);
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginDto dto) {
        SecurityContext ctx = SecurityContextHolder.getContext();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        var user = userService.findByUsername(dto.getEmail());
        var token = jwtUtil.createToken(user, null);

        return ResponseEntity.ok(token);
    }

}
