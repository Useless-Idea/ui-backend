package space.uselessidea.uibackend.infrastructure.eve.api.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Data;

@Data
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
public class SkillsApiResponse {

  private List<Skill> skills;

  @Data
  @JsonNaming(
      PropertyNamingStrategies.SnakeCaseStrategy.class
  )
  public static class Skill {

    private Long activeSkillLevel;
    private Long skillId;
    private Long skillpointsInSkill;
    private Long trainedSkillLevel;

  }

}
