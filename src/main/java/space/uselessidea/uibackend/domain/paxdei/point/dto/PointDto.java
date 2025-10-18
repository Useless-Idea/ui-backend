package space.uselessidea.uibackend.domain.paxdei.point.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import space.uselessidea.uibackend.infrastructure.paxdei.persistence.PointEntity;

@Builder
@Data
public class PointDto {

  private UUID uuid;
  private Long x;
  private Long y;
  private String text;

  public static PointDto from(PointEntity pointEntity) {
    return PointDto.builder()
        .uuid(pointEntity.getUuid())
        .x(pointEntity.getX())
        .y(pointEntity.getY())
        .text(pointEntity.getText())
        .build();
  }

}
