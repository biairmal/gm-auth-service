package id.gudangmeme.authservice.dto.in;

import lombok.Getter;

@Getter
public class RegisterDto {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
