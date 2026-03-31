package space.uselessidea.uibackend.domain.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import space.uselessidea.uibackend.domain.character.port.secondary.CharacterSecondaryPort;
import space.uselessidea.uibackend.domain.eve.api.dto.SkillDto;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.security.UserContext;
import space.uselessidea.uibackend.domain.token.port.primary.TokenPrimaryPort;

class CharacterServiceTest {

  @Test
  void shouldRejectSkillsReadForDifferentCharacterWithoutAdminRole() {
    CharacterSecondaryPort characterSecondaryPort = mock(CharacterSecondaryPort.class);
    TokenPrimaryPort tokenPrimaryPort = mock(TokenPrimaryPort.class);
    EveApiPort eveApiPort = mock(EveApiPort.class);
    CharacterService characterService =
        new CharacterService(characterSecondaryPort, tokenPrimaryPort, eveApiPort);

    UserContext requester =
        UserContext.builder().characterId(11L).roles(Set.of("USER")).permissions(Set.of()).build();

    assertThrows(ApplicationException.class, () -> characterService.getUserSkills(12L, requester));
  }

  @Test
  void shouldReturnSkillsForAdminRequester() {
    CharacterSecondaryPort characterSecondaryPort = mock(CharacterSecondaryPort.class);
    TokenPrimaryPort tokenPrimaryPort = mock(TokenPrimaryPort.class);
    EveApiPort eveApiPort = mock(EveApiPort.class);
    CharacterService characterService =
        new CharacterService(characterSecondaryPort, tokenPrimaryPort, eveApiPort);

    UserContext admin =
        UserContext.builder().characterId(11L).roles(Set.of("ADMIN")).permissions(Set.of()).build();
    Map<Long, SkillDto> skills =
        Map.of(
            3300L, SkillDto.builder().skillId(3300L).name("Gunnery").activeSkillLevel(5L).build());

    when(tokenPrimaryPort.getAccessToken(12L)).thenReturn(Optional.of("access-token"));
    when(eveApiPort.getUserSkills(12L, "access-token")).thenReturn(skills);

    Map<Long, SkillDto> result = characterService.getUserSkills(12L, admin);

    assertEquals(skills, result);
  }

  @Test
  void shouldRejectCharacterPageForNonAdmin() {
    CharacterSecondaryPort characterSecondaryPort = mock(CharacterSecondaryPort.class);
    TokenPrimaryPort tokenPrimaryPort = mock(TokenPrimaryPort.class);
    EveApiPort eveApiPort = mock(EveApiPort.class);
    CharacterService characterService =
        new CharacterService(characterSecondaryPort, tokenPrimaryPort, eveApiPort);

    UserContext requester =
        UserContext.builder().characterId(11L).roles(Set.of("USER")).permissions(Set.of()).build();

    assertThrows(
        ApplicationException.class,
        () -> characterService.getCharacterDataPage(PageRequest.of(0, 10), requester));
  }
}
