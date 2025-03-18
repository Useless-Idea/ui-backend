package space.uselessidea.uibackend.domain.character;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;
import space.uselessidea.uibackend.domain.character.port.primary.CharacterPrimaryPort;
import space.uselessidea.uibackend.domain.character.port.secondary.CharacterSecondaryPort;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.token.port.primary.TokenPrimaryPort;
import space.uselessidea.uibackend.infrastructure.eve.api.Skill;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterService implements CharacterPrimaryPort {

  private final CharacterSecondaryPort characterSecondaryPort;
  private final TokenPrimaryPort tokenPrimaryPort;
  private final EveApiPort eveApiPort;

  @Override
  public void changeTokenStatus(Long charId, boolean tokenStatus) {
    CharactedData characterData = characterSecondaryPort.getCharacterData(
        charId);
    characterData.setTokenActive(tokenStatus);
    characterData = characterSecondaryPort.saveCharacterData(characterData);
  }

  @Override

  public Map<Long, Skill> getUserSkills(Long characterId, CharacterPrincipal principal) {
    canGetUserSkills(characterId, principal);
    String accessToken = tokenPrimaryPort.getAccessToken(characterId);
    return eveApiPort.getUserSkills(
        characterId, accessToken);

  }

  @Override
  public CharactedData getCharacterData(Long characterId) {
    return characterSecondaryPort.getCharacterData(characterId);
  }

  private void canGetUserSkills(Long characterId, CharacterPrincipal principal) {
    if (principal.getCharacterId().equals(characterId)) {
      return;
    }
    if (principal.getRoles().contains("ADMIN")) {
      return;
    }
    throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
  }


}
