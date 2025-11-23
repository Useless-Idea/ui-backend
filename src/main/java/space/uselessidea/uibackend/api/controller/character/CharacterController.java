package space.uselessidea.uibackend.api.controller.character;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.character.dto.CharacterFeature;

@RestController
@RequestMapping("/api/v1/character")
@RequiredArgsConstructor
@Slf4j
public class CharacterController {

  private final CharacterApiService characterApiService;

  @GetMapping("/{id}/skills")
  public ResponseEntity getSkills(@PathVariable("id") Long characterId, CharacterPrincipal characterPrincipal) {
    return ResponseEntity.ok(characterApiService.getUserSkills(characterId, characterPrincipal));

  }


  /**
   * Get Character By Id
   *
   * @param characterId        characterId
   * @param characterPrincipal characterPrincipal
   * @return characterData
   */
  @GetMapping("/{id}")
  public ResponseEntity getCharacter(@PathVariable("id") Long characterId, CharacterPrincipal characterPrincipal) {
    return ResponseEntity.ok(characterApiService.getCharacter(characterId, characterPrincipal));
  }

  @GetMapping("")
  public ResponseEntity getCharacterPage(Pageable pageable, CharacterPrincipal characterPrincipal) {
    return ResponseEntity.ok(characterApiService.getCharacters(pageable, characterPrincipal));
  }

  @GetMapping("/scope")
  public ResponseEntity<Set<CharacterFeature>> getScope(CharacterPrincipal principal) {

    Long characterId = principal.getCharacterId();
    Set<CharacterFeature> response = characterApiService.getCharacterFeatures(characterId);

    return ResponseEntity.ok(response);
  }

}
