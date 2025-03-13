package space.uselessidea.uibackend.infrastructure.eve.auth.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
public class TokenData {

  private String accessToken;
  private String refreshToken;
  private Long expiresIn;

}
