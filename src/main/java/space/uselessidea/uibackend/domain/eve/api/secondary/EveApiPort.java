package space.uselessidea.uibackend.domain.eve.api.secondary;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import space.uselessidea.uibackend.domain.category.dto.CategoryDto;
import space.uselessidea.uibackend.domain.group.dto.GroupDto;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;
import space.uselessidea.uibackend.infrastructure.eve.api.Skill;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CorporationPublicData;

public interface EveApiPort {

  CharacterPublicData getCharPublicData(Long charId);

  CorporationPublicData getCorporationPublicData(Long corporationId);

  Map<Long, Skill> getUserSkills(Long characterId, String accessToken);

  Set<Long> getAllItemTypeId();

  Optional<ItemTypeDto> getItemByItemTypeId(Long itemTypeId);

  CategoryDto getCategoryDataById(Long id);

  GroupDto getGroupDataById(Long groupId);
}
