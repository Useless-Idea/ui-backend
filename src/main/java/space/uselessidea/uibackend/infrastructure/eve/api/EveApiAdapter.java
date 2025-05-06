package space.uselessidea.uibackend.infrastructure.eve.api;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.category.dto.CategoryDto;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.group.dto.GroupDto;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CorporationPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.ItemTypeApiResponse;
import space.uselessidea.uibackend.infrastructure.eve.api.data.ItemTypeApiResponse.Attribute;
import space.uselessidea.uibackend.infrastructure.eve.api.data.SkillsApiResponse;

@Component
@RequiredArgsConstructor
public class EveApiAdapter implements EveApiPort {

  private final EveApiFeignClient eveApiFeignClient;

  @Override
  public CharacterPublicData getCharPublicData(Long charId) {
    return eveApiFeignClient.getCharacterPublicData(charId);
  }

  @Override
  public CorporationPublicData getCorporationPublicData(Long corporationId) {
    return eveApiFeignClient.getCorporationPublicData(corporationId);
  }

  @Override
  @Cacheable(value = "user-skill", key = "#characterId")
  public Map<Long, Skill> getUserSkills(Long characterId, String accessToken) {
    SkillsApiResponse skillsApiResponse = eveApiFeignClient.getCharacterSkills(characterId, accessToken);
    Map<Long, Skill> map = toSkillMap(skillsApiResponse);
    return map;
  }

  @Override
  public Set<Long> getAllItemTypeId() {
    Set<Long> idSet = new HashSet<>();
    int pages = 1;
    for (int page = 0; page < pages; page++) {
      ResponseEntity<Set<Long>> response = eveApiFeignClient.getItemTypeIds(page + 1);
      String xpagesValueStr = response.getHeaders().getFirst("x-pages");
      pages = Integer.parseInt(xpagesValueStr);
      idSet.addAll(response.getBody());
    }
    return idSet;
  }

  @Override
  public Optional<ItemTypeDto> getItemByItemTypeId(Long itemTypeId) {
    ItemTypeApiResponse response = eveApiFeignClient.getItemByItemTypeId(itemTypeId);
    if (!response.getPublished()) {
      return Optional.empty();
    }
    Set<Long> requiredSkillsAttributeSet = new HashSet<>();
    requiredSkillsAttributeSet.addAll(RequiredSkillsUtils.REQUIRED_SKILL_MAPPING_MAP.keySet());
    requiredSkillsAttributeSet.addAll(RequiredSkillsUtils.REQUIRED_SKILL_MAPPING_MAP.values());
    Set<Attribute> requiredSkillsSet = response.getDogmaAttributes().stream()
        .filter(a -> requiredSkillsAttributeSet.contains(a.getAttributeId())).collect(
            Collectors.toSet());
    Map<Long, Long> requiredSkillMap = RequiredSkillsUtils.attributesToRequiredSkillMap(requiredSkillsSet);
    ItemTypeDto itemTypeDto = ItemTypeDto.builder()
        .itemId(itemTypeId)
        .name(response.getName())
        .requiredSkillMap(requiredSkillMap)
        .build();
    return Optional.of(itemTypeDto);
  }

  @Override
  public CategoryDto getCategoryDataById(Long id) {
    return eveApiFeignClient.getCategoryDataById(id);
  }

  @Override
  public GroupDto getGroupDataById(Long groupId) {
    return eveApiFeignClient.getGroupDataById(groupId);
  }

  public ItemTypeApiResponse getType(Long typeId) {
    return eveApiFeignClient.getItemByItemTypeId(typeId);
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
