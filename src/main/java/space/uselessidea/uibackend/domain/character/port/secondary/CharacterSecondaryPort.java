package space.uselessidea.uibackend.domain.character.port.secondary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;

public interface CharacterSecondaryPort {

  CharactedData getCharacterData(Long id);

  CharactedData saveCharacterData(CharactedData characterData);

  Page<CharactedData> getCharacterDataPage(Pageable pageable);
}
