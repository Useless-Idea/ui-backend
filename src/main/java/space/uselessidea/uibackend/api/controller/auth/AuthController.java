package space.uselessidea.uibackend.api.controller.auth;

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
public class AuthController {


  private final AuthApiService authApiService;

  @GetMapping("/me")
  public ResponseEntity<AuthMeResponse> authMe(CharacterPrincipal principal) {
    AuthMeResponse authMeResponse = authApiService.getMe(principal);
    return ResponseEntity.ok(authMeResponse);
  }

  @GetMapping("/login")
  public ResponseEntity<String> generateUrlForToken(@RequestParam Set<FeatureEnum> featureEnums) {
    String url = authApiService.generateUrlForToken(featureEnums);
    return ResponseEntity.ok(url);
  }

  @GetMapping("/callback")
  public ResponseEntity<String> callback(@RequestParam String code, @RequestParam String state) {
    authApiService.handleCallback(code, state);
    return ResponseEntity.ok().build();
  }

}
