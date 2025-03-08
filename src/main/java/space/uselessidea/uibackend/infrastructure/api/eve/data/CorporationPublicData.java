package space.uselessidea.uibackend.infrastructure.api.eve.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
public class CorporationPublicData {

  private String name;
  private Long allianceId;
  private Long ceoId;
  private String ticker;

}
