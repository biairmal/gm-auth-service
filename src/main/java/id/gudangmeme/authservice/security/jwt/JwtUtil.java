package id.gudangmeme.authservice.security.jwt;

import id.gudangmeme.authservice.user.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${application.security.jwt.access-token.expiration}")
    private long accessTokenExpirationInSeconds;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpirationInSeconds;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String createAccessToken(UserAccount userAccount, Map<String, Object> extraClaims) {
        return generateToken(userAccount, extraClaims, accessTokenExpirationInSeconds);
    }

    public String createRefreshToken(UserAccount userAccount, Map<String, Object> extraClaims) {
        return generateToken(userAccount, extraClaims, refreshTokenExpirationInSeconds);
    }

    private String generateToken(UserAccount userAccount, Map<String, Object> extraClaims, long tokenExpiration) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userAccount.getUserIdentity().getUsername())
                .issuer("gm-auth-server")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
