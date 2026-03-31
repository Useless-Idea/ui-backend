package space.uselessidea.uibackend.domain.character.port.primary;

import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;
import space.uselessidea.uibackend.domain.character.dto.CharacterFeature;
import space.uselessidea.uibackend.domain.eve.api.dto.SkillDto;
import space.uselessidea.uibackend.domain.security.UserContext;

public interface CharacterPrimaryPort {

  void changeTokenStatus(Long charId, boolean tokenStatus);

  Set<Long> getCharacterIds();

  Map<Long, SkillDto> getUserSkills(Long characterId, UserContext principal);

  CharactedData getCharacterData(Long characterId, UserContext principal);

  CharactedData updateCharacterData(Long characterId);

  Page<CharactedData> getCharacterDataPage(Pageable pageable, UserContext characterPrincipal);

  Map<Long, String> getCharacterIdNameMap(UserContext characterPrincipal);

  boolean hasRequiredSkills(Long characterId, Map<Long, Long> requiredSkills);

  Set<CharacterFeature> getFeatureScope(Long characterId);
}
