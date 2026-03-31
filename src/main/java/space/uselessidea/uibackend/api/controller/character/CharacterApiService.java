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
import space.uselessidea.uibackend.domain.eve.api.dto.SkillDto;
import space.uselessidea.uibackend.domain.security.UserContext;

@Service
@RequiredArgsConstructor
public class CharacterApiService {

  private final CharacterPrimaryPort characterPrimaryPort;

  public Map<Long, SkillDto> getUserSkills(
      Long characterId, CharacterPrincipal characterPrincipal) {
    return characterPrimaryPort.getUserSkills(characterId, toUserContext(characterPrincipal));
  }

  public CharactedData getCharacter(Long characterId, CharacterPrincipal characterPrincipal) {
    return characterPrimaryPort.getCharacterData(characterId, toUserContext(characterPrincipal));
  }

  public Page<CharactedData> getCharacters(
      Pageable pageable, CharacterPrincipal characterPrincipal) {
    return characterPrimaryPort.getCharacterDataPage(pageable, toUserContext(characterPrincipal));
  }

  public Set<CharacterFeature> getCharacterFeatures(Long characterId) {
    return characterPrimaryPort.getFeatureScope(characterId);
  }

  public Map<Long, String> getCharacterIdNameMap(CharacterPrincipal characterPrincipal) {
    return characterPrimaryPort.getCharacterIdNameMap(toUserContext(characterPrincipal));
  }

  private UserContext toUserContext(CharacterPrincipal principal) {
    if (principal == null) {
      return null;
    }
    return UserContext.builder()
        .characterId(principal.getCharacterId())
        .charName(principal.getCharName())
        .corpId(principal.getCorpId())
        .roles(principal.getRoles())
        .permissions(principal.getPermissions())
        .build();
  }
}
