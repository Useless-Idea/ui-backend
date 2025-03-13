package space.uselessidea.uibackend.domain.character;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;
import space.uselessidea.uibackend.domain.character.port.primary.CharacterPrimaryPort;
import space.uselessidea.uibackend.domain.character.port.secondary.CharacterSecondaryPort;

@Service
@RequiredArgsConstructor
public class CharacterService implements CharacterPrimaryPort {

  private final CharacterSecondaryPort characterSecondaryPort;

  @Override
  public void changeTokenStatus(Long charId, boolean tokenStatus) {
    CharactedData characterData = characterSecondaryPort.getCharacterData(
        charId);
    characterData.setTokenActive(tokenStatus);
    characterData = characterSecondaryPort.saveCharacterData(characterData);

  }
}
