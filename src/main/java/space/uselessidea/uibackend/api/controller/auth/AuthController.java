package space.uselessidea.uibackend.api.controller.auth;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

  @GetMapping("/me")
  public ResponseEntity authMe(Principal principal) {
    log.info(principal.getName());
    return ResponseEntity.ok().build();
  }

}
