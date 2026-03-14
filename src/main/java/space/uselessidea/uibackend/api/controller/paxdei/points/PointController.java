package space.uselessidea.uibackend.api.controller.paxdei.points;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.domain.paxdei.point.dto.PointDto;

@RestController
@RequestMapping("/api/v1/pax_dei/point")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer")
@Tag(name = "Pax Dei Points", description = "Endpointy zarządzania punktami na mapie Pax Dei")
public class PointController {

  private final PointApiService pointApiService;

  @GetMapping
  @Operation(
      summary = "Pobiera wszystkie punkty",
      description = "Zwraca pełną listę zapisanych punktów mapy.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista punktów",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            [
                              {
                                "uuid": "9d54bcf6-7132-4d03-bf4f-0ed39fa6d101",
                                "xpos": 1520,
                                "ypos": 2870,
                                "text": "Ruda miedzi"
                              }
                            ]
                            """))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<Set<PointDto>> getAllPoints() {
    Set<PointDto> response = pointApiService.getAllPoints();
    return ResponseEntity.ok(response);
  }

  @PostMapping
  @Operation(
      summary = "Dodaje punkt",
      description = "Tworzy nowy punkt mapy na podstawie współrzędnych i opisu.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Punkt został zapisany"),
    @ApiResponse(responseCode = "400", description = "Niepoprawne dane wejściowe"),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<String> addPoint(@RequestBody final CreatePointRequest createPointRequest) {
    pointApiService.createPoint(createPointRequest);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{uuid}")
  @Operation(summary = "Usuwa punkt", description = "Usuwa punkt mapy o wskazanym UUID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Punkt został usunięty"),
    @ApiResponse(responseCode = "404", description = "Punkt nie istnieje"),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<String> deletePoint(@PathVariable("uuid") UUID uuid) {
    pointApiService.deletePoint(uuid);
    return ResponseEntity.ok().build();
  }
}
