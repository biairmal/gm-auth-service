package id.gudangmeme.authservice.user;

import id.gudangmeme.authservice.authentication.dto.RegisterRequest;

public interface UserService {

    UserAccount create(RegisterRequest dto);

    UserAccount findByUsername(String username);

}
