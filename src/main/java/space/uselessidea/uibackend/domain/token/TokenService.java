package space.uselessidea.uibackend.domain.token;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import space.uselessidea.uibackend.domain.FeatureEnum;
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
  private final RabbitTemplate rabbitTemplate;
  private final Queue characterUpdateQueue;

  private final ConcurrentHashMap<Long, Lock> locks = new ConcurrentHashMap<>();

  @Override
  public Long addToken(TokenData tokenData) {
    if (!eveAuthSecondaryPort.verifyToken(tokenData.getAccessToken())) {
      throw new ApplicationException(ErrorCode.ACCESS_TOKEN_IS_INVALID);
    }
    JwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(eveProperties.getIssuerUri()).build();
    Jwt jwt = jwtDecoder.decode(tokenData.getAccessToken());
    Instant expiresAt = jwt.getExpiresAt();
    Long charId = Long.valueOf(jwt.getClaimAsString("sub").split(":")[2]);
    List<String> scpList = jwt.getClaimAsStringList("scp");
    Set<FeatureEnum> featureSet = FeatureEnum.mapFromScpList(scpList);

    tokenSecondaryPort.saveToken(charId, expiresAt, tokenData, featureSet);
    rabbitTemplate.convertAndSend(characterUpdateQueue.getName(), charId);
    return charId;
  }

  @Override
  @Cacheable(value = "access-token",
      key = "#characterId",
      unless = "#result == null || #result.isEmpty()")
  public Optional<String> getAccessToken(Long characterId) {
    Lock lock = locks.computeIfAbsent(characterId, id -> new ReentrantLock());
    lock.lock();
    try {
      return tokenSecondaryPort.getToken(characterId)
          .flatMap(this::refreshToken);

    } catch (HttpClientErrorException.BadRequest e) {
      // logujemy błąd, ale nie wrzucamy do cache
      log.warn("Nie idało się pobrać tokena dla characterId: {}", characterId);
      return Optional.empty();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void refreshAllTokens() {
    tokenSecondaryPort.getAllTokens().forEach(this::refreshToken);
  }

  public Optional<String> refreshToken(EsiTokenDto esiTokenDto) {
    TokenData tokenData = null;
    try {
      tokenData = eveAuthSecondaryPort.refreshToken(esiTokenDto.getRefreshToken());
    } catch (HttpClientErrorException e) {
      log.error("Error during getting new access token by refresh token for: {}", esiTokenDto.getId(), e);
      //tokenSecondaryPort.deleteToken(esiTokenDto.getId());
      return Optional.empty();

    }
    try {
      addToken(tokenData);
    } catch (ApplicationException e) {
      log.error("Error during adding new access token by refresh token for: {}", esiTokenDto.getId(), e);
      if (ErrorCode.ACCESS_TOKEN_IS_INVALID.equals(e.getErrorCode())) {

        //tokenSecondaryPort.deleteToken(esiTokenDto.getId());
        return Optional.empty();
      }
    }
    return Optional.of(tokenData.getAccessToken());
  }
}
