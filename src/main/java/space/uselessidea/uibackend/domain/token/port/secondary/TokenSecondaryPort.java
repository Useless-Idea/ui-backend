package space.uselessidea.uibackend.domain.token.port.secondary;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import space.uselessidea.uibackend.domain.FeatureEnum;
import space.uselessidea.uibackend.domain.token.dto.EsiTokenDto;
import space.uselessidea.uibackend.domain.token.dto.TokenDataDto;

public interface TokenSecondaryPort {

  EsiTokenDto saveToken(
      Long charId, Instant expiresAt, TokenDataDto tokenData, Set<FeatureEnum> featureSet);

  Optional<EsiTokenDto> getToken(Long charId);

  List<EsiTokenDto> getAllTokens();

  void deleteToken(Long id);
}
