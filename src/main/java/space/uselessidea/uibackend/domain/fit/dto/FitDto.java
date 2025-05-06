package space.uselessidea.uibackend.domain.fit.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FitDto implements Serializable {

  private UUID uuid;
  private String name;
  private String shipName;
  @Builder.Default
  private Map<Long, Long> requiredSkills = new HashMap<>();

}
