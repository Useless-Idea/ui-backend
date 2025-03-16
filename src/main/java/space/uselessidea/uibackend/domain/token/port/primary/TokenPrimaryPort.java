package space.uselessidea.uibackend.domain.token.port.primary;

import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;

public interface TokenPrimaryPort {

  Long addToken(TokenData tokenData);

  String getAccessToken(Long characterId);

  void refreshAllTokens();
}
