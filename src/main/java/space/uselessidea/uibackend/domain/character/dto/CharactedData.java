package space.uselessidea.uibackend.domain.character.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharactedData {

  private Long characterId;
  private String characterName;
  private Long corporationId;
  private String corporationName;
  private Long allianceId;
  private String allianceName;
  private Set<String> roles = new HashSet<>();
  private Set<String> permission = new HashSet<>();
  private Boolean isBlocked;


}
