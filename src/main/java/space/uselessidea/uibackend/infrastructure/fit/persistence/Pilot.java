package space.uselessidea.uibackend.infrastructure.fit.persistence;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Pilot {

  private Long id;
  private String name;

}
