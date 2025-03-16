package space.uselessidea.uibackend.domain.eve.api.secondary;

import java.util.Map;
import space.uselessidea.uibackend.infrastructure.eve.api.Skill;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CorporationPublicData;

public interface EveApiPort {

  CharacterPublicData getCharPublicData(Long charId);

  CorporationPublicData getCorporationPublicData(Long corporationId);

  Map<Long, Skill> getUserSkills(Long characterId, String accessToken);

}
