package space.uselessidea.uibackend.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
@Builder
public class AuthMeResponse {

  private String charName;
  private Long charId;
  private String corpName;
  private Long corpId;
  private Set<String> roles;


}
