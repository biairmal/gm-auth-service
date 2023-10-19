package id.gudangmeme.authservice.user;

import id.gudangmeme.authservice.authentication.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserIdentityRepository userIdentityRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserIdentityRepository userIdentityRepository, UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userIdentityRepository = userIdentityRepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccount create(RegisterRequest dto) {
        LocalDateTime now = LocalDateTime.now();

        UserIdentity userIdentity = UserIdentity.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .createdAt(now)
                .build();

        userIdentityRepository.save(userIdentity);

        UserAccount userAccount = UserAccount.builder()
                .emailAddress(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .createdAt(now)
                .isVerified(false)
                .userIdentity(userIdentity)
                .build();

        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }


}
