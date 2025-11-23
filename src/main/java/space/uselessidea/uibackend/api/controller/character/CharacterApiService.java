package space.uselessidea.uibackend.api.controller.character;

import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;
import space.uselessidea.uibackend.domain.character.dto.CharacterFeature;
import space.uselessidea.uibackend.domain.character.port.primary.CharacterPrimaryPort;
import space.uselessidea.uibackend.infrastructure.eve.api.Skill;

@Service
@RequiredArgsConstructor
public class CharacterApiService {

  private final CharacterPrimaryPort characterPrimaryPort;

  public Map<Long, Skill> getUserSkills(Long characterId, CharacterPrincipal characterPrincipal) {
    return characterPrimaryPort.getUserSkills(characterId, characterPrincipal);

  }

  public CharactedData getCharacter(Long characterId, CharacterPrincipal characterPrincipal) {
    return characterPrimaryPort.getCharacterData(characterId, characterPrincipal);
  }

  public Page<CharactedData> getCharacters(Pageable pageable, CharacterPrincipal characterPrincipal) {
    return characterPrimaryPort.getCharacterDataPage(pageable, characterPrincipal);
  }

  public Set<CharacterFeature> getCharacterFeatures(Long characterId) {
    return characterPrimaryPort.getFeatureScope(characterId);
  }
}
