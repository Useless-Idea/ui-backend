package space.uselessidea.uibackend.infrastructure.eve.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import space.uselessidea.uibackend.infrastructure.eve.api.data.ItemTypeApiResponse.Attribute;

public class RequiredSkillsUtils {

  public static Map<Long, Long> REQUIRED_SKILL_MAPPING_MAP = Map.of(
      182L, 277L,       //requiredSkill1,requiredSkill1Level
      183L, 278L,           //requiredSkill2,requiredSkill2Level
      184L, 279L,           //requiredSkill3,requiredSkill3Level
      1285L, 1286L,         //requiredSkill4,requiredSkill4Level
      1289L, 1287L,         //requiredSkill5,requiredSkill5Level
      1290L, 1288L);        //requiredSkill6,requiredSkill6Level


  public static Map<Long, Long> attributesToRequiredSkillMap(Set<Attribute> attributeSet) {
    Map<Long, Long> idValueMap = new HashMap<>();

    for (Entry<Long, Long> entry : REQUIRED_SKILL_MAPPING_MAP.entrySet()) {
      Optional<Attribute> keyAttribute = attributeSet.stream()
          .filter(attribute -> attribute.getAttributeId().equals(entry.getKey())).findFirst();
      if (keyAttribute.isEmpty()) {
        continue;
      }

      Attribute valueAttribute = attributeSet.stream()
          .filter(attribute -> attribute.getAttributeId().equals(entry.getValue())).findFirst().get();
      idValueMap.put(Double.valueOf(keyAttribute.get().getValue()).longValue(),
          Double.valueOf(valueAttribute.getValue()).longValue());
    }
    return idValueMap;
  }

}
