package space.uselessidea.uibackend.domain.token;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.token.dto.EsiTokenDto;
import space.uselessidea.uibackend.domain.token.port.primary.TokenPrimaryPort;
import space.uselessidea.uibackend.domain.token.port.secondary.EveAuthSecondaryPort;
import space.uselessidea.uibackend.domain.token.port.secondary.TokenSecondaryPort;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;
import space.uselessidea.uibackend.properties.EveProperties;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService implements TokenPrimaryPort {

  private final EveAuthSecondaryPort eveAuthSecondaryPort;
  private final TokenSecondaryPort tokenSecondaryPort;
  private final EveProperties eveProperties;

  @Override
  public Long addToken(TokenData tokenData) {
    if (!eveAuthSecondaryPort.verifyToken(tokenData.getAccessToken())) {
      throw new ApplicationException(ErrorCode.ACCESS_TOKEN_IS_INVALID);
    }
    JwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(eveProperties.getIssuerUri()).build();
    Jwt jwt = jwtDecoder.decode(tokenData.getAccessToken());
    Instant expiresAt = jwt.getExpiresAt();
    Long charId = Long.valueOf(jwt.getClaimAsString("sub").split(":")[2]);

    tokenSecondaryPort.saveToken(charId, expiresAt, tokenData);
    return charId;
  }

  @Override
  public void refreshAllTokens() {
    tokenSecondaryPort.getAllTokens().forEach(this::refreshToken);
  }

  public void refreshToken(EsiTokenDto esiTokenDto) {
    TokenData tokenData = eveAuthSecondaryPort.refreshToken(esiTokenDto.getRefreshToken());
    try {
      addToken(tokenData);
    } catch (ApplicationException e) {
      log.error(e.getMessage());
      if (ErrorCode.ACCESS_TOKEN_IS_INVALID.equals(e.getErrorCode())) {
        tokenSecondaryPort.deleteToken(esiTokenDto.getId());

      }

    }

  }
}
