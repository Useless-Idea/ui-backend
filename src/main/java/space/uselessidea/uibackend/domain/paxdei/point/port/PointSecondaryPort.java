package space.uselessidea.uibackend.domain.paxdei.point.port;

import java.util.Set;
import java.util.UUID;
import space.uselessidea.uibackend.api.controller.paxdei.points.CreatePointRequest;
import space.uselessidea.uibackend.domain.paxdei.point.dto.PointDto;

public interface PointSecondaryPort {

  void createPoint(CreatePointRequest createPointRequest);


  Set<PointDto> getAllPoints();

  void deletePoint(UUID uuid);
}
