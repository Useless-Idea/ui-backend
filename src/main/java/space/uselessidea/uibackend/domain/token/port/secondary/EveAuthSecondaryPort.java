package space.uselessidea.uibackend.domain.token.port.secondary;

import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;

public interface EveAuthSecondaryPort {

  boolean verifyToken(String token);

  TokenData refreshToken(String refreshToken);

  TokenData handleCallback(String code);
}
