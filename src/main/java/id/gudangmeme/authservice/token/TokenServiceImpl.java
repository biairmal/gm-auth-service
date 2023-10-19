package id.gudangmeme.authservice.token;

import id.gudangmeme.authservice.security.jwt.JwtUtil;
import id.gudangmeme.authservice.user.UserAccount;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    public TokenServiceImpl(TokenRepository tokenRepository, JwtUtil jwtUtil) {
        this.tokenRepository = tokenRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Token generateUserToken(UserAccount userAccount) {
        // remove all active token on database
        invalidateUserActiveTokens(userAccount.getUserIdentity().getId());

        // generate new token
        String accessToken = jwtUtil.createToken(userAccount, null);
        Date accessTokenExpiration = jwtUtil.extractExpiration(accessToken);

        Token token = Token.builder()
                .userIdentityId(userAccount.getUserIdentity().getId())
                .accessToken(accessToken)
                .refreshToken(null)
                .isExpired(false)
                .isRevoked(false)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.ofInstant(accessTokenExpiration.toInstant(), ZoneId.systemDefault()))
                .build();

        return tokenRepository.save(token);
    }

    @Override
    public void invalidateUserActiveTokens(UUID userIdentityId) {
        List<Token> tokens = tokenRepository.findUserActiveTokens(userIdentityId);

        tokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        tokenRepository.saveAll(tokens);
    }

    @Override
    public boolean isTokenStillValid(String accessToken) {
        Token token = tokenRepository.findByAccessToken(accessToken);

        return !token.isExpired() && !token.isRevoked();
    }

    @Override
    public Token getStoredTokenByAccessToken(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken);
    }

}
