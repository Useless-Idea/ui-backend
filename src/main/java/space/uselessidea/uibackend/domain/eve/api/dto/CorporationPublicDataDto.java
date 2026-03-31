package space.uselessidea.uibackend.domain.eve.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CorporationPublicDataDto {

  private String name;
  private Long allianceId;
  private Long ceoId;
  private String ticker;
}
