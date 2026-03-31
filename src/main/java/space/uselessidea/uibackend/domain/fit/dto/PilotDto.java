package space.uselessidea.uibackend.domain.fit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PilotDto {

  private Long id;
  private String name;
}
