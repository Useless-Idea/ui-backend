package space.uselessidea.uibackend.infrastructure.eve.api;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill implements Serializable {

  private String name;
  private Long activeSkillLevel;
  private Long skillId;
  private Long skillpointsInSkill;
  private Long trainedSkillLevel;

}
