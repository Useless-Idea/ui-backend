package space.uselessidea.uibackend.api.controller.fit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.api.controller.fit.dto.SimpleListFit;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;
import space.uselessidea.uibackend.domain.fit.dto.SearchFitDto;

@RestController("/fit")
@RequiredArgsConstructor
@RequestMapping("/api/v1/fit")
@SecurityRequirement(name = "Bearer")
@Tag(name = "Fit", description = "Endpointy zarządzania fitami")
public class FitController {

  private final FitApiService fitApiService;

  @PostMapping
  @Operation(
      summary = "Dodaje nowy fit",
      description = "Tworzy nowy fit na podstawie EFT, opisu i doktryn.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Dodany fit",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            {
                              "uuid": "7f7c5a4e-451f-4df9-9dc6-2b3f3f8fd001",
                              "name": "Drake Fleet",
                              "shipId": 24698,
                              "shipName": "Drake",
                              "pilots": {
                                "active": [{ "id": 90000001, "name": "Capsuleer Prime" }],
                                "inactive": []
                              },
                              "eft": "[Drake, Fleet]\\nBallistic Control System II",
                              "description": "Podstawowy fit do roamingu.",
                              "doctrines": ["Shield", "Missile"]
                            }
                            """))),
    @ApiResponse(responseCode = "400", description = "Niepoprawne dane wejściowe"),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<FitDto> addFit(@RequestBody FitForm fitForm) {
    FitDto fitDto = fitApiService.addFit(fitForm);
    return ResponseEntity.ok(fitDto);
  }

  @PutMapping("/{uuid}")
  @Operation(
      summary = "Edytuje istniejący fit",
      description =
          "Aktualizuje EFT, doktryny i metadane fita. W trakcie edycji czyści listę pilotów i dodaje fit do kolejki ponownego przeliczenia pilotów.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Zaktualizowany fit",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            {
                              "uuid": "7f7c5a4e-451f-4df9-9dc6-2b3f3f8fd001",
                              "name": "Drake Fleet Updated",
                              "shipId": 24698,
                              "shipName": "Drake",
                              "eft": "[Drake, Fleet Updated]\\nBallistic Control System II",
                              "pilots": { "active": [], "inactive": [] },
                              "doctrines": ["Shield", "Missile"]
                            }
                            """))),
    @ApiResponse(responseCode = "400", description = "Fit nie istnieje lub niepoprawne dane"),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<FitDto> editFit(
      @PathVariable("uuid") UUID fitUuid, @RequestBody FitForm fitForm) {
    return ResponseEntity.ok(fitApiService.editFit(fitUuid, fitForm));
  }

  @GetMapping("/{uuid}")
  @Operation(
      summary = "Pobiera pojedynczy fit",
      description = "Zwraca szczegóły fita na podstawie UUID.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Szczegóły fita",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            {
                              "uuid": "7f7c5a4e-451f-4df9-9dc6-2b3f3f8fd001",
                              "name": "Drake Fleet",
                              "shipId": 24698,
                              "shipName": "Drake",
                              "eft": "[Drake, Fleet]\\nBallistic Control System II",
                              "pilots": {
                                "active": [{ "id": 90000001, "name": "Capsuleer Prime" }],
                                "inactive": []
                              },
                              "doctrines": ["Shield", "Missile"]
                            }
                            """))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji"),
    @ApiResponse(responseCode = "404", description = "Fit nie istnieje")
  })
  public ResponseEntity<FitDto> getFitByUuid(@PathVariable("uuid") UUID fitUuid) {
    return ResponseEntity.ok(fitApiService.getFitByUuid(fitUuid));
  }

  @GetMapping
  @Operation(
      summary = "Wyszukuje fity",
      description =
          "Zwraca stronicowaną listę fitów filtrowaną po nazwie, pilotach, statkach i doktrynach.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Strona fitów",
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
                                  "uuid": "7f7c5a4e-451f-4df9-9dc6-2b3f3f8fd001",
                                  "name": "Drake Fleet",
                                  "shipName": "Drake",
                                  "pilots": {
                                    "active": [{ "id": 90000001, "name": "Capsuleer Prime" }],
                                    "inactive": []
                                  },
                                  "shipId": 24698
                                }
                              ],
                              "totalElements": 1,
                              "totalPages": 1,
                              "size": 10,
                              "number": 0
                            }
                            """))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<Page<SimpleListFit>> getFit(
      @RequestParam(required = false) String fitName,
      @RequestParam(required = false) List<String> pilots,
      @RequestParam(required = false) List<String> ships,
      @RequestParam(required = false) List<String> doctrines,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    SearchFitDto normalizedSearchFitDto =
        SearchFitDto.builder()
            .fitName(fitName)
            .pilots(pilots)
            .ships(ships)
            .doctrines(doctrines)
            .page(page)
            .size(size)
            .build()
            .normalizeLists();
    return ResponseEntity.ok(fitApiService.getFits(normalizedSearchFitDto));
  }

  @GetMapping("/map")
  @Operation(
      summary = "Pobiera mapę nazw statków",
      description = "Zwraca mapę: nazwa statku -> shipId.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Mapa statków",
        content =
            @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"Drake\":24698,\"Caracal\":621}"))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<Map<String, Long>> getShipNameIdMap() {
    return ResponseEntity.ok(fitApiService.getShipNameIdMap());
  }

  @GetMapping("/doctrines")
  @Operation(
      summary = "Pobiera listę doktryn",
      description = "Zwraca unikalne nazwy doktryn używanych w zapisanych fitach.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista doktryn",
        content =
            @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "[\"Shield\",\"Armor\",\"Missile\"]"))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<Set<String>> getDoctrines() {
    return ResponseEntity.ok(fitApiService.getDoctrines());
  }

  @DeleteMapping("/{uuid}")
  @Operation(summary = "Usuwa fit", description = "Usuwa fit o wskazanym UUID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Fit usunięty"),
    @ApiResponse(responseCode = "400", description = "Fit nie istnieje"),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<Void> deleteFit(@PathVariable("uuid") UUID fitUuid) {
    fitApiService.deleteFit(fitUuid);
    return ResponseEntity.ok().build();
  }
}
