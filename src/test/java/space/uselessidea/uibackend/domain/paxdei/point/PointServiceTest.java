package space.uselessidea.uibackend.domain.paxdei.point;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import space.uselessidea.uibackend.domain.paxdei.point.dto.CreatePointCommand;
import space.uselessidea.uibackend.domain.paxdei.point.port.PointSecondaryPort;

class PointServiceTest {

  @Test
  void shouldDelegateCreatePointToSecondaryPort() {
    PointSecondaryPort pointSecondaryPort = mock(PointSecondaryPort.class);
    PointService pointService = new PointService(pointSecondaryPort);
    CreatePointCommand command =
        CreatePointCommand.builder().xpos(100L).ypos(200L).text("Ore").build();

    pointService.createPoint(command);

    verify(pointSecondaryPort).createPoint(command);
  }

  @Test
  void shouldDelegateDeletePointToSecondaryPort() {
    PointSecondaryPort pointSecondaryPort = mock(PointSecondaryPort.class);
    PointService pointService = new PointService(pointSecondaryPort);
    UUID uuid = UUID.randomUUID();

    pointService.deletePoint(uuid);

    verify(pointSecondaryPort).deletePoint(uuid);
  }
}
