package space.uselessidea.uibackend.domain.character.port.primary;

import java.util.Map;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.infrastructure.eve.api.Skill;

public interface CharacterPrimaryPort {

  void changeTokenStatus(Long charId, boolean tokenStatus);

  Map<Long, Skill> getUserSkills(Long characterId, CharacterPrincipal principal);

}
