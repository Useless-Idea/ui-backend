package space.uselessidea.uibackend.domain.token.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;
import space.uselessidea.uibackend.domain.FeatureEnum;

@Builder
@Data
public class EsiTokenDto {

  private Long id;

  private String refreshToken;

  private Set<FeatureEnum> features;

}
