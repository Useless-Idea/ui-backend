package space.uselessidea.uibackend.api.controller.fit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;

@RestController("/fit")
@RequiredArgsConstructor
public class FitController {

  private final FitApiService fitApiService;

  @PostMapping
  public ResponseEntity addFit(@RequestBody FitForm fitForm) {
    FitDto fitDto = fitApiService.addFit(fitForm);
    return ResponseEntity.ok(fitDto);
  }

}
