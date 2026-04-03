package space.uselessidea.uibackend.domain.token.port.primary;

import java.util.Optional;
import space.uselessidea.uibackend.domain.token.dto.TokenDataDto;

public interface TokenPrimaryPort {

  Long addToken(TokenDataDto tokenData);

  Optional<String> getAccessToken(Long characterId);
}
