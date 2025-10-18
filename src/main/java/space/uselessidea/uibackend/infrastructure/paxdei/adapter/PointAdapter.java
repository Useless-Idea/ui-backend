package space.uselessidea.uibackend.infrastructure.paxdei.adapter;

import jakarta.transaction.Transactional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.api.controller.paxdei.points.CreatePointRequest;
import space.uselessidea.uibackend.domain.paxdei.point.dto.PointDto;
import space.uselessidea.uibackend.domain.paxdei.point.port.PointSecondaryPort;
import space.uselessidea.uibackend.infrastructure.paxdei.persistence.PointEntity;
import space.uselessidea.uibackend.infrastructure.paxdei.repository.PointRepository;

@Service
@RequiredArgsConstructor
public class PointAdapter implements PointSecondaryPort {

  private final PointRepository pointRepository;

  @Override
  @Transactional
  public void createPoint(CreatePointRequest createPointRequest) {
    PointEntity point = new PointEntity();
    point.setXpos(createPointRequest.getXpos());
    point.setYpos(createPointRequest.getYpos());
    point.setText(createPointRequest.getText());
    pointRepository.save(point);
  }

  @Override
  public Set<PointDto> getAllPoints() {
    return pointRepository.findAll().stream().map(PointDto::from).collect(Collectors.toSet());
  }

  @Override
  public void deletePoint(UUID uuid) {
    pointRepository.deleteById(uuid);
  }
}
