package space.uselessidea.uibackend.api.controller.skill;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Skill", description = "Endpointy mapowania grup i skilli")
public class SkillController {

  private final SkillApiService skillApiService;

  @GetMapping("/map")
  @Operation(
      summary = "Pobiera mapę grup skilli",
      description = "Zwraca mapę: nazwa grupy -> zestaw ID typów (skilli).")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Mapa grup i skilli",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            {
                              "Gunnery": [3300, 3301, 3302],
                              "Missiles": [3319, 3320]
                            }
                            """)))
  })
  public ResponseEntity getSkillMap() {
    return ResponseEntity.ok(skillApiService.getGroupSkillMapping());
  }
}
