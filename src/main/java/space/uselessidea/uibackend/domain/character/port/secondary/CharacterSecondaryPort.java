package space.uselessidea.uibackend.domain.character.port.secondary;

import space.uselessidea.uibackend.domain.character.dto.CharactedData;

public interface CharacterSecondaryPort {

  CharactedData getCharacterData(Long id);

  CharactedData saveCharacterData(CharactedData characterData);
}
