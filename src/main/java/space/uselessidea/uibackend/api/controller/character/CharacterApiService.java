package space.uselessidea.uibackend.api.controller.character;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.character.port.primary.CharacterPrimaryPort;
import space.uselessidea.uibackend.infrastructure.eve.api.Skill;

@Service
@RequiredArgsConstructor
public class CharacterApiService {

  private final CharacterPrimaryPort characterPrimaryPort;

  public Map<Long, Skill> getUserSkills(Long characterId, CharacterPrincipal characterPrincipal) {
    return characterPrimaryPort.getUserSkills(characterId, characterPrincipal);

  }

}
