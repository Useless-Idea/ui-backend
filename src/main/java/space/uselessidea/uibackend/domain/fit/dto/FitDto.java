package space.uselessidea.uibackend.domain.fit.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FitDto implements Serializable {

  private UUID uuid;
  private String name;
  private Long shipId;
  private String shipName;
  private PilotsDto pilots;
  private String eft;
  private String description;
  private List<String> doctrines;
}
