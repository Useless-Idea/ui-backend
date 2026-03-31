package space.uselessidea.uibackend.domain.eve.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterPublicDataDto {

  private String name;
  private Long corporationId;
  private Long allianceId;
}
