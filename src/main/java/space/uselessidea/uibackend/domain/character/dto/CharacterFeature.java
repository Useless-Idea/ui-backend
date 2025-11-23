package space.uselessidea.uibackend.domain.character.dto;

import lombok.Builder;
import lombok.Data;
import space.uselessidea.uibackend.domain.FeatureEnum;

@Data
@Builder
public class CharacterFeature {

  private FeatureEnum feature;
  private boolean active;

}
