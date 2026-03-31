package space.uselessidea.uibackend.domain.token.port.secondary;

import space.uselessidea.uibackend.domain.token.dto.TokenDataDto;

public interface EveAuthSecondaryPort {

  boolean verifyToken(String token);

  TokenDataDto refreshToken(String refreshToken);

  TokenDataDto handleCallback(String code);
}
