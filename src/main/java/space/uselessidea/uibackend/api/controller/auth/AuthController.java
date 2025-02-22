package space.uselessidea.uibackend.api.controller.auth;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.api.config.security.JwtUserToken;
import space.uselessidea.uibackend.domain.auth.AuthService;
import space.uselessidea.uibackend.domain.auth.dto.AuthMeResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  @GetMapping("/me")
  public ResponseEntity<AuthMeResponse> authMe(CharacterPrincipal principal) {
    AuthMeResponse response = authService.getMe(principal);
    return ResponseEntity.ok(response);
  }

}
