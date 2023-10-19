package id.gudangmeme.authservice.security;

import id.gudangmeme.authservice.token.Token;
import id.gudangmeme.authservice.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenService tokenService;

    @Autowired
    public CustomLogoutHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String tokenPrefix = "Bearer ";
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            return;
        }

        String accessToken = authHeader.substring(tokenPrefix.length());
        Token token = tokenService.getStoredTokenByAccessToken(accessToken);
        tokenService.invalidateUserActiveTokens(token.getUserIdentityId());
    }
}
