package space.uselessidea.uibackend.infrastructure.api.eve;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.infrastructure.api.eve.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.api.eve.data.CorporationPublicData;

@Component
@RequiredArgsConstructor
public class EveApiAdapter implements EveApiPort {

  private final EveApiFeignClient eveApiFeignClient;

  @Override
  public CharacterPublicData getCharPublicData(Long charId) {
    return eveApiFeignClient.getCharacterPublicData(charId);
  }

  @Override
  public CorporationPublicData getCorporationPublicData(Long corporationId) {
    return eveApiFeignClient.getCorporationPublicData(corporationId);
  }
}
