package space.uselessidea.uibackend.infrastructure.eve.api.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
public class TypeData implements Serializable {


  private Long typeId;
  private String name;
  private Boolean published;

}
