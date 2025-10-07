package space.uselessidea.uibackend.domain.token.port.primary;

import java.util.Optional;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;

public interface TokenPrimaryPort {

  Long addToken(TokenData tokenData);

  Optional<String> getAccessToken(Long characterId);

  void refreshAllTokens();
}
