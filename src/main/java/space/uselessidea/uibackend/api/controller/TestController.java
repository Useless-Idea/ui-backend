package space.uselessidea.uibackend.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@Tag(name = "Test", description = "Endpoint testowy API")
public class TestController {

  @GetMapping
  @Operation(
      summary = "Sprawdza dostępność API",
      description = "Prosty health-check endpoint zwracający status 200.")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "API działa poprawnie")})
  public ResponseEntity testMethod() {
    return ResponseEntity.ok().build();
  }
}
