package space.uselessidea.uibackend.api.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.FeatureEnum;
import space.uselessidea.uibackend.domain.auth.dto.AuthMeResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth", description = "Endpointy autoryzacji i obsługi tożsamości użytkownika")
public class AuthController {

  private final AuthApiService authApiService;

  @GetMapping("/me")
  @Operation(
      summary = "Pobiera dane zalogowanego użytkownika",
      description = "Zwraca podstawowe informacje o postaci oraz zestaw ról i uprawnień.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Dane użytkownika",
        content =
            @Content(
                mediaType = "application/json",
                examples =
                    @ExampleObject(
                        value =
                            """
                            {
                              "char_name": "Capsuleer Prime",
                              "char_id": 90000001,
                              "corp_name": "Useless Idea Corp",
                              "corp_id": 98000001,
                              "permission": ["FIT_READ", "FIT_WRITE"],
                              "roles": ["ADMIN", "FC"]
                            }
                            """))),
    @ApiResponse(responseCode = "401", description = "Brak autoryzacji")
  })
  public ResponseEntity<AuthMeResponse> authMe(CharacterPrincipal principal) {
    AuthMeResponse authMeResponse = authApiService.getMe(principal);
    return ResponseEntity.ok(authMeResponse);
  }

  @GetMapping("/login")
  @Operation(
      summary = "Generuje URL logowania EVE SSO",
      description =
          "Na podstawie przekazanych funkcjonalności tworzy adres autoryzacyjny EVE OAuth2 z odpowiednim zakresem scope.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Wygenerowany URL logowania",
        content =
            @Content(
                mediaType = "text/plain",
                examples =
                    @ExampleObject(
                        value =
                            "https://login.eveonline.com/v2/oauth/authorize?response_type=code&redirect_uri=https://app.example.com/api/v1/auth/callback&client_id=client-id&scope=esi-skills.read_skills.v1&state=4f8f114a-7b7a-4d6b-bf07-4f4a6f0f0001"))),
    @ApiResponse(responseCode = "400", description = "Niepoprawne parametry zapytania")
  })
  public ResponseEntity<String> generateUrlForToken(@RequestParam Set<FeatureEnum> featureEnums) {
    String url = authApiService.generateUrlForToken(featureEnums);
    return ResponseEntity.ok(url);
  }

  @GetMapping("/callback")
  @Operation(
      summary = "Obsługuje callback z EVE SSO",
      description =
          "Przyjmuje kod autoryzacyjny i stan, pobiera tokeny, publikuje je do kolejki i kończy przepływ logowania.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Callback obsłużony poprawnie"),
    @ApiResponse(responseCode = "400", description = "Brak wymaganych parametrów code/state")
  })
  public ResponseEntity<String> callback(@RequestParam String code, @RequestParam String state) {
    authApiService.handleCallback(code, state);
    return ResponseEntity.ok().build();
  }
}
