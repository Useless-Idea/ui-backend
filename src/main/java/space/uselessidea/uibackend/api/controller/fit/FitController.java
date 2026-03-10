package space.uselessidea.uibackend.api.controller.fit;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<Page<SimpleListFit>> getFit(@RequestBody SearchFitDto searchFitDto) {
    return ResponseEntity.ok(fitApiService.getFits(searchFitDto));
  }
}
