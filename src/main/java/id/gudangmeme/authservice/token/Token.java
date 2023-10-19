package id.gudangmeme.authservice.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authentication_token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID userIdentityId;
    private String accessToken;
    private String refreshToken;
    private boolean isExpired;
    private boolean isRevoked;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

}
