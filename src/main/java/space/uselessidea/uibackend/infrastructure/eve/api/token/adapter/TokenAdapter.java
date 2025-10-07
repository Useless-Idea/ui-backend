package space.uselessidea.uibackend.infrastructure.eve.api.token.adapter;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.FeatureEnum;
import space.uselessidea.uibackend.domain.token.dto.EsiTokenDto;
import space.uselessidea.uibackend.domain.token.port.secondary.TokenSecondaryPort;
import space.uselessidea.uibackend.infrastructure.eve.api.token.persistence.EsiToken;
import space.uselessidea.uibackend.infrastructure.eve.api.token.repository.EsiTokenRepository;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenAdapter implements TokenSecondaryPort {

  private final EsiTokenRepository esiTokenRepository;


  @Override
  public EsiTokenDto saveToken(Long charId, Instant expiresAt, TokenData tokenData, Set<FeatureEnum> featureSet) {
    EsiToken esiToken = esiTokenRepository.findById(charId).orElseGet(() -> {
      EsiToken token = new EsiToken();
      token.setId(charId);
      return token;
    });
    esiToken.setRefreshToken(tokenData.getRefreshToken());
    esiToken.setFeatures(featureSet);
    esiToken = esiTokenRepository.save(esiToken);
    return map(esiToken);
  }

  @Override
  public Optional<EsiTokenDto> getToken(Long charId) {
    //TODO to ma być obsłużone w redisie
    /*
    jak token jest w redis to spoko a jak nie to
    refresh i robimy na optionalu
     */
    return esiTokenRepository.findById(charId)
        .map(this::map);
  }

  @Override
  public List<EsiTokenDto> getAllTokens() {
    return esiTokenRepository.findAll()
        .stream()
        .map(this::map)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteToken(Long id) {
    esiTokenRepository.deleteById(id);
    log.info("Delete Token for char[{}]", id);
  }

  private EsiTokenDto map(EsiToken esiToken) {
    return EsiTokenDto.builder()
        .id(esiToken.getId())
        .refreshToken(esiToken.getRefreshToken())
        .features(esiToken.getFeatures())
        .build();

  }
}
