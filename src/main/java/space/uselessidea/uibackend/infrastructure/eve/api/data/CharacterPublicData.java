package space.uselessidea.uibackend.infrastructure.eve.api.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
public class CharacterPublicData {

  private String name;
  private Long corporationId;
  private Long allianceId;

}
