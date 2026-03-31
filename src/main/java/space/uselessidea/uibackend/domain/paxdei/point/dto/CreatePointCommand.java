package space.uselessidea.uibackend.domain.paxdei.point.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePointCommand {

  private Long xpos;
  private Long ypos;
  private String text;
}
