package space.uselessidea.uibackend.domain.itemtype.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemTypeDto {

  private Long itemId;
  private String name;
  @Builder.Default
  private Map<Long, Long> requiredSkillMap = new HashMap<>();

}
