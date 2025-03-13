package space.uselessidea.uibackend.domain.token.port.secondary;

import java.time.Instant;
import java.util.List;
import space.uselessidea.uibackend.domain.token.dto.EsiTokenDto;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;

public interface TokenSecondaryPort {


  EsiTokenDto saveToken(Long charId, Instant expiresAt, TokenData tokenData);

  void getToken(Long charId);

  List<EsiTokenDto> getAllTokens();

  void deleteToken(Long id);
}
