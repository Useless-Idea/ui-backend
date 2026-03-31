package space.uselessidea.uibackend.domain.fit.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PilotsDto {

  private List<PilotDto> active;
  private List<PilotDto> inactive;
}
