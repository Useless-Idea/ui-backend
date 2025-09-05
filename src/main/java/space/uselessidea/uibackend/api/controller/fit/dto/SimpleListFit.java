package space.uselessidea.uibackend.api.controller.fit.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilots;

@Builder
@Data
public class SimpleListFit {

  private UUID uuid;
  private String name;
  private String shipName;
  private Pilots pilots;
  private Long shipId;


  public static SimpleListFit fromFitDto(FitDto fitDto) {
    return SimpleListFit.builder()
        .uuid(fitDto.getUuid())
        .name(fitDto.getName())
        .pilots(fitDto.getPilots())
        .shipId(fitDto.getShipId())
        .shipName(fitDto.getShipName()).build();
  }
}
