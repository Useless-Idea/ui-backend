package space.uselessidea.uibackend.domain.eve.api.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto implements Serializable {

  private String name;
  private Long activeSkillLevel;
  private Long skillId;
  private Long skillpointsInSkill;
  private Long trainedSkillLevel;
}
