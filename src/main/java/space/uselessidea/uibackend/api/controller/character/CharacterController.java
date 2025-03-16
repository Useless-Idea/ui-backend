package space.uselessidea.uibackend.api.controller.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;

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

}
