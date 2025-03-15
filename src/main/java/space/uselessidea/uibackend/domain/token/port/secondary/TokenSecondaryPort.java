package space.uselessidea.uibackend.domain.token.port.secondary;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import space.uselessidea.uibackend.domain.FeatureEnum;
import space.uselessidea.uibackend.domain.token.dto.EsiTokenDto;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;

public interface TokenSecondaryPort {


  EsiTokenDto saveToken(Long charId, Instant expiresAt, TokenData tokenData, Set<FeatureEnum> featureSet);

  void getToken(Long charId);

  List<EsiTokenDto> getAllTokens();

  void deleteToken(Long id);
}
