package space.uselessidea.uibackend.infrastructure.eve.api;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CorporationPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.SkillsApiResponse;
import space.uselessidea.uibackend.infrastructure.eve.api.data.TypeData;

@Component
@RequiredArgsConstructor
public class EveApiAdapter implements EveApiPort {

  private EveApiFeignClient eveApiFeignClient;

  @Override
  public CharacterPublicData getCharPublicData(Long charId) {
    return eveApiFeignClient.getCharacterPublicData(charId);
  }

  @Override
  public CorporationPublicData getCorporationPublicData(Long corporationId) {
    return eveApiFeignClient.getCorporationPublicData(corporationId);
  }

  @Override
  @Cacheable(value = "UserSkill", key = "#characterId")
  public Map<Long, Skill> getUserSkills(Long characterId, String accessToken) {
    SkillsApiResponse skillsApiResponse = eveApiFeignClient.getCharacterSkills(characterId, accessToken);
    Map<Long, Skill> map = toSkillMap(skillsApiResponse);
    return map;
  }

  public TypeData getType(Long typeId) {
    return eveApiFeignClient.getType(typeId);
  }


  private Map<Long, Skill> toSkillMap(SkillsApiResponse skillsApiResponse) {
    return skillsApiResponse.getSkills().stream()
        .map(skill ->
            Skill.builder()
                .skillId(skill.getSkillId())
                .activeSkillLevel(skill.getActiveSkillLevel())
                .skillpointsInSkill(skill.getSkillpointsInSkill())
                .trainedSkillLevel(skill.getTrainedSkillLevel())
                .name(getType(skill.getSkillId()).getName())
                .build()
        ).collect(Collectors.toMap(Skill::getSkillId, s -> s));

  }


}
