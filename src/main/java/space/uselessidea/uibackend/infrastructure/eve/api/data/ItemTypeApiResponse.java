package space.uselessidea.uibackend.infrastructure.eve.api.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
public class ItemTypeApiResponse implements Serializable {

  private String name;
  private Boolean published;
  private List<Attribute> dogmaAttributes = new ArrayList<>();
  private Long typeId;

  @Data
  @JsonNaming(
      PropertyNamingStrategies.SnakeCaseStrategy.class
  )
  public static class Attribute {

    private Long attributeId;
    private String value;

  }
}
