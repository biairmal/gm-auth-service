package id.gudangmeme.authservice.services;

import id.gudangmeme.authservice.dto.in.RegisterDto;
import id.gudangmeme.authservice.models.UserAccount;

public interface UserService {

    UserAccount create(RegisterDto dto);

}
