package space.uselessidea.uibackend.api.controller.fit;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class FitController {

  private final FitApiService fitApiService;

  @PostMapping
  public ResponseEntity<FitDto> addFit(@RequestBody FitForm fitForm) {
    FitDto fitDto = fitApiService.addFit(fitForm);
    return ResponseEntity.ok(fitDto);
  }

  @GetMapping
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
  public ResponseEntity<Map<String, Long>> getShipNameIdMap() {
    return ResponseEntity.ok(fitApiService.getShipNameIdMap());
  }

  @GetMapping("/doctrines")
  public ResponseEntity<List<String>> getDoctrines() {
    return ResponseEntity.ok(fitApiService.getDoctrines());
  }
}
