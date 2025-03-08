package space.uselessidea.uibackend.domain.character.port.secondrt;

import space.uselessidea.uibackend.domain.character.dto.CharactedData;

public interface CharacterPort {

  CharactedData getCharacterData(Long id);
}
