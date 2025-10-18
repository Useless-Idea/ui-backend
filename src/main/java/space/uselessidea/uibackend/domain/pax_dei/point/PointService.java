package space.uselessidea.uibackend.domain.pax_dei.point;

import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.api.controller.pax_dei.points.CreatePointRequest;
import space.uselessidea.uibackend.domain.pax_dei.point.dto.PointDto;
import space.uselessidea.uibackend.domain.pax_dei.point.port.PointPrimaryPoint;
import space.uselessidea.uibackend.domain.pax_dei.point.port.PointSecondaryPort;

@Service
@RequiredArgsConstructor
public class PointService implements PointPrimaryPoint {

  private final PointSecondaryPort pointSecondaryPort;

  @Override
  public void createPoint(CreatePointRequest createPointRequest) {
    pointSecondaryPort.createPoint(createPointRequest);
  }

  @Override
  public Set<PointDto> getAllPoints() {
    return pointSecondaryPort.getAllPoints();
  }

  @Override
  public void deletePoint(UUID uuid) {
    pointSecondaryPort.deletePoint(uuid);
  }
}
