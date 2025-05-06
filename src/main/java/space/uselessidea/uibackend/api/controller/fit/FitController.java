package space.uselessidea.uibackend.api.controller.fit;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;

@RestController("/fit")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class FitController {

  private final FitApiService fitApiService;

  @PostMapping
  public ResponseEntity addFit(@RequestBody FitForm fitForm) {
    FitDto fitDto = fitApiService.addFit(fitForm);
    return ResponseEntity.ok(fitDto);
  }

  @GetMapping
  public ResponseEntity getFit() {
    Set<FitDto> fits = fitApiService.getFits();
    return ResponseEntity.ok(fits);
  }

}
