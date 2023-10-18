package id.gudangmeme.authservice.authentication;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String password;

}
