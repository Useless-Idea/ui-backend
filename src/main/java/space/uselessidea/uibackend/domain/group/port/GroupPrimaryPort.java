package space.uselessidea.uibackend.domain.group.port;

import space.uselessidea.uibackend.domain.group.dto.GroupDto;

public interface GroupPrimaryPort {

  GroupDto getGroupDataById(Long groupId);

}
