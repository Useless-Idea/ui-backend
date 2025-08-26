package space.uselessidea.uibackend.domain.fit.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilots;

@Builder
@Data
public class FitDto implements Serializable {

  private UUID uuid;
  private String name;
  private String shipName;
  private Pilots pilots;

}
