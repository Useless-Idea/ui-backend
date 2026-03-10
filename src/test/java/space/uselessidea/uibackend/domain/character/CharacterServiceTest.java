package space.uselessidea.uibackend.domain.character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;
import space.uselessidea.uibackend.domain.character.port.secondary.CharacterSecondaryPort;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.token.port.primary.TokenPrimaryPort;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

  @Mock private CharacterSecondaryPort characterSecondaryPort;
  @Mock private TokenPrimaryPort tokenPrimaryPort;
  @Mock private EveApiPort eveApiPort;
  @Mock private CharacterPrincipal characterPrincipal;

  private CharacterService characterService;

  @BeforeEach
  void setUp() {
    characterService = new CharacterService(characterSecondaryPort, tokenPrimaryPort, eveApiPort);
  }

  @Test
  void getCharacterIdNameMapReturnsIdNameMapForAdmin() {
    when(characterPrincipal.getRoles()).thenReturn(Set.of("ADMIN"));

    List<CharactedData> characters =
        List.of(
            CharactedData.builder().characterId(1L).characterName("Alpha").build(),
            CharactedData.builder().characterId(2L).characterName("Beta").build());
    Page<CharactedData> page =
        new PageImpl<>(characters, PageRequest.of(0, Integer.MAX_VALUE), characters.size());
    when(characterSecondaryPort.getCharacterDataPage(any(Pageable.class))).thenReturn(page);

    Map<Long, String> result = characterService.getCharacterIdNameMap(characterPrincipal);

    assertThat(result).containsEntry(1L, "Alpha").containsEntry(2L, "Beta");
    ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
    verify(characterSecondaryPort).getCharacterDataPage(pageableCaptor.capture());
    assertThat(pageableCaptor.getValue().getPageNumber()).isZero();
    assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(Integer.MAX_VALUE);
  }

  @Test
  void getCharacterIdNameMapRequiresAdminRole() {
    when(characterPrincipal.getRoles()).thenReturn(Set.of("USER"));

    ApplicationException exception =
        assertThrows(
            ApplicationException.class,
            () -> characterService.getCharacterIdNameMap(characterPrincipal));

    assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_PERMISSION);
    verifyNoInteractions(characterSecondaryPort);
  }
}
