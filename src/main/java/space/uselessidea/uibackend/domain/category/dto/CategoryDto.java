package space.uselessidea.uibackend.domain.category.dto;

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
public class CategoryDto {

  private Long categoryId;
  @Builder.Default
  private Set<Long> groups = new HashSet<>();
  private String name;
  private boolean published;

}
