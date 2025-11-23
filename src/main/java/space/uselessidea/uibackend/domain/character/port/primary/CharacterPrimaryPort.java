package space.uselessidea.uibackend.domain.character.port.primary;

import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;
import space.uselessidea.uibackend.domain.character.dto.CharacterFeature;
import space.uselessidea.uibackend.infrastructure.eve.api.Skill;

public interface CharacterPrimaryPort {

  void changeTokenStatus(Long charId, boolean tokenStatus);

  Set<Long> getCharacterIds();

  Map<Long, Skill> getUserSkills(Long characterId, CharacterPrincipal principal);

  CharactedData getCharacterData(Long characterId, CharacterPrincipal principal);

  CharactedData updateCharacterData(Long characterId);

  Page<CharactedData> getCharacterDataPage(Pageable pageable, CharacterPrincipal characterPrincipal);

  boolean hasRequiredSkills(Long characterId, Map<Long, Long> requiredSkills);

  Set<CharacterFeature> getFeatureScope(Long characterId);
}
