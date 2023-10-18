package id.gudangmeme.authservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    UserAccount findByEmailAddress(String email);

    @Query("SELECT acc FROM UserAccount acc WHERE acc.userIdentity.username=?1")
    UserAccount findByUsername(String username);

}
