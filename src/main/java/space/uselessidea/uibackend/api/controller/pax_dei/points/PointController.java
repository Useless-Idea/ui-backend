package space.uselessidea.uibackend.api.controller.pax_dei.points;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.domain.pax_dei.point.dto.PointDto;

@RestController
@RequestMapping("/api/v1/pax_dei/point")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer")
public class PointController {

  private final PointApiService pointApiService;

  @GetMapping
  public ResponseEntity<Set<PointDto>> getAllPoints() {
    Set<PointDto> response = pointApiService.getAllPoints();
    return ResponseEntity.ok(response);

  }

  @PostMapping
  public ResponseEntity<String> addPoint(@RequestBody final CreatePointRequest createPointRequest) {
    pointApiService.createPoint(createPointRequest);
    return ResponseEntity.ok("Point added");
  }

}
