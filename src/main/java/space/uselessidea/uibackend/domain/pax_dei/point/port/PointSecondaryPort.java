package space.uselessidea.uibackend.domain.pax_dei.point.port;

import java.util.Set;
import java.util.UUID;
import space.uselessidea.uibackend.api.controller.pax_dei.points.CreatePointRequest;
import space.uselessidea.uibackend.domain.pax_dei.point.dto.PointDto;

public interface PointSecondaryPort {

  void createPoint(CreatePointRequest createPointRequest);


  Set<PointDto> getAllPoints();

  void deletePoint(UUID uuid);
}
