package space.uselessidea.uibackend.api.controller.pax_dei.points;

import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.pax_dei.point.dto.PointDto;
import space.uselessidea.uibackend.domain.pax_dei.point.port.PointPrimaryPoint;

@Service
@RequiredArgsConstructor
public class PointApiService {

  private final PointPrimaryPoint pointPrimaryPoint;

  public void createPoint(CreatePointRequest createPointRequest) {
    pointPrimaryPoint.createPoint(createPointRequest);
  }

  public Set<PointDto> getAllPoints() {
    return pointPrimaryPoint.getAllPoints();
  }

  public void deletePoint(UUID uuid) {
    pointPrimaryPoint.deletePoint(uuid);
  }
}
