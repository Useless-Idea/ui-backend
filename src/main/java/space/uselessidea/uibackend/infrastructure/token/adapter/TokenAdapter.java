package space.uselessidea.uibackend.infrastructure.token.adapter;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.token.dto.EsiTokenDto;
import space.uselessidea.uibackend.domain.token.port.secondary.TokenSecondaryPort;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;
import space.uselessidea.uibackend.infrastructure.token.persistence.EsiToken;
import space.uselessidea.uibackend.infrastructure.token.repository.EsiTokenRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenAdapter implements TokenSecondaryPort {

  private final EsiTokenRepository esiTokenRepository;


  @Override
  public EsiTokenDto saveToken(Long charId, Instant expiresAt, TokenData tokenData) {
    EsiToken esiToken = esiTokenRepository.findById(charId).orElseGet(() -> {
      EsiToken token = new EsiToken();
      token.setId(charId);
      return token;
    });
    esiToken.setExpDate(expiresAt);
    esiToken.setJwt(tokenData.getAccessToken());
    esiToken.setRefreshToken(tokenData.getRefreshToken());
    esiToken = esiTokenRepository.save(esiToken);
    return map(esiToken);
  }

  @Override
  public void getToken(Long charId) {

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
        .jwt(esiToken.getJwt())
        .refreshToken(esiToken.getRefreshToken())
        .expDate(esiToken.getExpDate())
        .build();

  }
}
