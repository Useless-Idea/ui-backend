package space.uselessidea.uibackend.api.controller.skill;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.category.dto.CategoryDto;
import space.uselessidea.uibackend.domain.category.port.CategoryPrimaryPort;
import space.uselessidea.uibackend.domain.group.dto.GroupDto;
import space.uselessidea.uibackend.domain.group.port.GroupPrimaryPort;

@Service
@RequiredArgsConstructor
public class SkillApiService {

  private final CategoryPrimaryPort categoryPrimaryPort;
  private final GroupPrimaryPort groupPrimaryPort;
  private static final Long SKILL_CATEGORY_ID = 16L;

  public Map<String, Set<Long>> getGroupSkillMapping() {
    CategoryDto categoryData = categoryPrimaryPort.getCategoryDataById(
        SKILL_CATEGORY_ID);
    return categoryData.getGroups().stream()
        .map(groupPrimaryPort::getGroupDataById)
        .filter(GroupDto::isPublished)
        .collect(Collectors.toMap(
            GroupDto::getName,
            GroupDto::getTypes
        ));

  }

}
