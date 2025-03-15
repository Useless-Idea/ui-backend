package space.uselessidea.uibackend.domain.token.dto;

import java.time.Instant;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import space.uselessidea.uibackend.domain.FeatureEnum;

@Builder
@Data
public class EsiTokenDto {

  private Long id;

  private Instant expDate;

  private String refreshToken;

  private String jwt;
  private Set<FeatureEnum> features;


}
