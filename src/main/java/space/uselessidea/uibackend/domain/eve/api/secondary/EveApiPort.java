package space.uselessidea.uibackend.domain.eve.api.secondary;

import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CorporationPublicData;

public interface EveApiPort {

  CharacterPublicData getCharPublicData(Long charId);

  CorporationPublicData getCorporationPublicData(Long corporationId);

}
