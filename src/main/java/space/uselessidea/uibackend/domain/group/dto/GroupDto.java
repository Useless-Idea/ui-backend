package space.uselessidea.uibackend.domain.group.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
public class GroupDto {

  private Long categoryId;
  private Long groupId;
  private String name;
  private boolean published;
  @Builder.Default
  private Set<Long> types = new HashSet<>();

}
