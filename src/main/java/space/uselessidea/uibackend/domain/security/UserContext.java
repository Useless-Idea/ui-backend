package space.uselessidea.uibackend.domain.security;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserContext {

  private Long characterId;
  private String charName;
  private Long corpId;
  private Set<String> roles;
  private Set<String> permissions;
}
