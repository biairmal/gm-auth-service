package id.gudangmeme.authservice.controllers;

import id.gudangmeme.authservice.dto.in.RegisterDto;
import id.gudangmeme.authservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {


    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto dto) {
        var result = userService.create(dto);

        return ResponseEntity.ok(result);
    }

}
