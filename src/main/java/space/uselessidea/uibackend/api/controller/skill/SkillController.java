package space.uselessidea.uibackend.api.controller.skill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
@Slf4j
public class SkillController {

  private final SkillApiService skillApiService;

  @GetMapping("/map")
  public ResponseEntity getSkillMap() {
    return ResponseEntity.ok(skillApiService.getGroupSkillMapping());
  }

}
