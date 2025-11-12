package space.uselessidea.uibackend.api.controller.fit;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.api.controller.fit.dto.SimpleListFit;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;

@RestController("/fit")
@RequiredArgsConstructor
@RequestMapping("/api/v1/fit")
@SecurityRequirement(name = "Bearer")
public class FitController {

  private final FitApiService fitApiService;

  @PostMapping
  public ResponseEntity addFit(@RequestBody FitForm fitForm) {
    FitDto fitDto = fitApiService.addFit(fitForm);
    return ResponseEntity.ok(fitDto);
  }

  @GetMapping
  public ResponseEntity<Page<SimpleListFit>> getFit() {
    List<SimpleListFit> fits = new ArrayList<>(fitApiService.getFits());
    Pageable pageable = PageRequest.of(1, Integer.MAX_VALUE);
    PageImpl<SimpleListFit> response = new PageImpl<>(fits, pageable, fits.size());
    return ResponseEntity.ok(response);
  }

}
