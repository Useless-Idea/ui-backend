package space.uselessidea.uibackend.infrastructure.fit.persistence;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pilots {

  private List<Pilot> active;
  private List<Pilot> inactive;

}
