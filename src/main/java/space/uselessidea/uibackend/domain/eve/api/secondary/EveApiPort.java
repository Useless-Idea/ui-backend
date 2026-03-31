package space.uselessidea.uibackend.domain.eve.api.secondary;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import space.uselessidea.uibackend.domain.category.dto.CategoryDto;
import space.uselessidea.uibackend.domain.eve.api.dto.CharacterPublicDataDto;
import space.uselessidea.uibackend.domain.eve.api.dto.CorporationPublicDataDto;
import space.uselessidea.uibackend.domain.eve.api.dto.SkillDto;
import space.uselessidea.uibackend.domain.group.dto.GroupDto;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;

public interface EveApiPort {

  CharacterPublicDataDto getCharPublicData(Long charId);

  CorporationPublicDataDto getCorporationPublicData(Long corporationId);

  Map<Long, SkillDto> getUserSkills(Long characterId, String accessToken);

  Set<Long> getAllItemTypeId();

  Optional<ItemTypeDto> getItemByItemTypeId(Long itemTypeId);

  CategoryDto getCategoryDataById(Long id);

  GroupDto getGroupDataById(Long groupId);
}
