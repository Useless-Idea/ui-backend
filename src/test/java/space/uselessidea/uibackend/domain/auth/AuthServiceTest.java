package space.uselessidea.uibackend.domain.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.junit.jupiter.api.Test;
import space.uselessidea.uibackend.domain.auth.dto.AuthMeResponse;
import space.uselessidea.uibackend.domain.eve.api.dto.CorporationPublicDataDto;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.security.UserContext;

class AuthServiceTest {

  @Test
  void shouldBuildAuthMeResponseFromUserContextAndCorporationData() {
    EveApiPort eveApiPort = mock(EveApiPort.class);
    AuthService authService = new AuthService(eveApiPort);

    UserContext userContext =
        UserContext.builder()
            .characterId(1001L)
            .charName("Lord Paridae")
            .corpId(987654L)
            .roles(Set.of("ADMIN"))
            .permissions(Set.of("FIT_EDIT"))
            .build();

    when(eveApiPort.getCorporationPublicData(987654L))
        .thenReturn(CorporationPublicDataDto.builder().name("Paridae Nest").build());

    AuthMeResponse response = authService.getMe(userContext);

    assertEquals(1001L, response.getCharId());
    assertEquals("Lord Paridae", response.getCharName());
    assertEquals(987654L, response.getCorpId());
    assertEquals("Paridae Nest", response.getCorpName());
    assertEquals(Set.of("ADMIN"), response.getRoles());
    assertEquals(Set.of("FIT_EDIT"), response.getPermission());
  }
}
