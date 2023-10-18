package id.gudangmeme.authservice.authentication;

import lombok.Getter;

@Getter
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}