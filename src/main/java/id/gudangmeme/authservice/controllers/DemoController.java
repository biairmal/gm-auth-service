package id.gudangmeme.authservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("demo")
public class DemoController {

    @GetMapping("test-user")
    public String testUser(Principal principal) {
        return "Hello, " + principal.getName();
    }
    
}
