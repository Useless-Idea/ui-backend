package space.uselessidea.uibackend.domain.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.group.dto.GroupDto;
import space.uselessidea.uibackend.domain.group.port.GroupPrimaryPort;

@Service
@RequiredArgsConstructor
public class GroupService implements GroupPrimaryPort {

  private final EveApiPort eveApiPort;

  @Override
  public GroupDto getGroupDataById(Long groupId) {
    return eveApiPort.getGroupDataById(groupId);
  }
}
