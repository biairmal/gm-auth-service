package id.gudangmeme.authservice.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query("""
            SELECT t FROM Token t
            WHERE t.userIdentityId=?1 and (t.isExpired = false or t.isRevoked = false)
            """)
    List<Token> findUserActiveTokens(UUID userIdentityId);

    Token findByAccessToken(String accessToken);

    Token findByRefreshToken(String accessToken);

}
