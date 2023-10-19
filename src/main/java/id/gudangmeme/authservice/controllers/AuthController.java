package id.gudangmeme.authservice.controllers;

import id.gudangmeme.authservice.authentication.AuthenticationService;
import id.gudangmeme.authservice.authentication.dto.LoginRequest;
import id.gudangmeme.authservice.authentication.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
