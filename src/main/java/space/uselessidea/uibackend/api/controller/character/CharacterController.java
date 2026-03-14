package space.uselessidea.uibackend.api.controller.character;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
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
@SecurityRequirement(name = "Bearer")
@Tag(name = "Character", description = "Endpointy danych postaci oraz uprawnień")
public class CharacterController {

  private final CharacterApiService characterApiService;

  @GetMapping("/{id}/skills")
  @Operation(
      summary = "Pobiera umiejętności postaci",
      description = "Zwraca mapę: skillId -> szczegóły umiejętności dla wskazanej postaci.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Mapa umiejętności",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            {
                              "3300": {
                                "name": "Gunnery",
                                "activeSkillLevel": 5,
                                "skillId": 3300,
                                "skillpointsInSkill": 256000,
                                "trainedSkillLevel": 5
                              }
                            }
                            """))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji"),
    @ApiResponse(responseCode = "403", description = "Brak dostępu do postaci")
  })
  public ResponseEntity getSkills(
      @PathVariable("id") Long characterId,
      @Parameter(hidden = true) CharacterPrincipal characterPrincipal) {
    return ResponseEntity.ok(characterApiService.getUserSkills(characterId, characterPrincipal));
  }

  /**
   * Get Character By Id
   *
   * @param characterId characterId
   * @param characterPrincipal characterPrincipal
   * @return characterData
   */
  @GetMapping("/{id}")
  @Operation(
      summary = "Pobiera dane postaci",
      description = "Zwraca szczegóły postaci, korporacji, sojuszu, role i uprawnienia.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Dane postaci",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            {
                              "characterId": 90000001,
                              "characterName": "Capsuleer Prime",
                              "corporationId": 98000001,
                              "corporationName": "Useless Idea Corp",
                              "allianceId": 99000001,
                              "allianceName": "Useless Idea Alliance",
                              "roles": ["ADMIN"],
                              "permission": ["FIT_READ", "FIT_WRITE"],
                              "isBlocked": false,
                              "tokenActive": true,
                              "version": 4
                            }
                            """))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji"),
    @ApiResponse(responseCode = "403", description = "Brak dostępu do postaci")
  })
  public ResponseEntity getCharacter(
      @PathVariable("id") Long characterId,
      @Parameter(hidden = true) CharacterPrincipal characterPrincipal) {
    return ResponseEntity.ok(characterApiService.getCharacter(characterId, characterPrincipal));
  }

  @GetMapping("")
  @Operation(
      summary = "Pobiera stronicowaną listę postaci",
      description = "Zwraca stronę danych postaci dostępnych dla zalogowanego użytkownika.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Strona z listą postaci",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            {
                              "content": [
                                {
                                  "characterId": 90000001,
                                  "characterName": "Capsuleer Prime",
                                  "corporationId": 98000001,
                                  "corporationName": "Useless Idea Corp",
                                  "allianceId": 99000001,
                                  "allianceName": "Useless Idea Alliance",
                                  "roles": ["ADMIN"],
                                  "permission": ["FIT_READ"],
                                  "isBlocked": false,
                                  "tokenActive": true,
                                  "version": 4
                                }
                              ],
                              "totalElements": 1,
                              "totalPages": 1,
                              "size": 20,
                              "number": 0
                            }
                            """))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity getCharacterPage(
      Pageable pageable, @Parameter(hidden = true) CharacterPrincipal characterPrincipal) {
    return ResponseEntity.ok(characterApiService.getCharacters(pageable, characterPrincipal));
  }

  @GetMapping("/map")
  @Operation(
      summary = "Pobiera mapę ID -> nazwa postaci",
      description = "Ułatwia budowanie list wyboru postaci po stronie klienta.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Mapa identyfikatorów i nazw",
        content =
            @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"90000001\":\"Capsuleer Prime\"}"))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<Map<Long, String>> getCharacterIdNameMap(
      @Parameter(hidden = true) CharacterPrincipal characterPrincipal) {
    return ResponseEntity.ok(characterApiService.getCharacterIdNameMap(characterPrincipal));
  }

  @GetMapping("/scope")
  @Operation(
      summary = "Pobiera aktywne funkcjonalności postaci",
      description = "Zwraca listę feature flag wraz ze stanem aktywności dla zalogowanej postaci.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista funkcjonalności",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            [
                              { "feature": "USER_SKILL", "active": true },
                              { "feature": "OPEN_CONTRACTS", "active": false }
                            ]
                            """))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<Set<CharacterFeature>> getScope(
      @Parameter(hidden = true) CharacterPrincipal principal) {

    Long characterId = principal.getCharacterId();
    Set<CharacterFeature> response = characterApiService.getCharacterFeatures(characterId);

    return ResponseEntity.ok(response);
  }
}
