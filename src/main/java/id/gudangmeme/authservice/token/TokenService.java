package id.gudangmeme.authservice.token;

import id.gudangmeme.authservice.user.UserAccount;

import java.util.UUID;

public interface TokenService {

    Token generateUserToken(UserAccount userAccount);

    void invalidateUserActiveTokens(UUID userIdentityId);

    boolean isTokenStillValid(String accessToken);

    Token getStoredTokenByAccessToken(String accessToken);

}
