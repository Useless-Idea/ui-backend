package space.uselessidea.uibackend.infrastructure.paxdei.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import space.uselessidea.uibackend.domain.paxdei.point.dto.CreatePointCommand;
import space.uselessidea.uibackend.domain.paxdei.point.dto.PointDto;
import space.uselessidea.uibackend.infrastructure.paxdei.persistence.PointEntity;
import space.uselessidea.uibackend.infrastructure.paxdei.repository.PointRepository;

class PointAdapterTest {

  @Test
  void shouldMapCreatePointCommandToPointEntity() {
    PointRepository pointRepository = mock(PointRepository.class);
    PointAdapter pointAdapter = new PointAdapter(pointRepository);
    CreatePointCommand command =
        CreatePointCommand.builder().xpos(11L).ypos(22L).text("Copper").build();

    pointAdapter.createPoint(command);

    ArgumentCaptor<PointEntity> captor = ArgumentCaptor.forClass(PointEntity.class);
    verify(pointRepository).save(captor.capture());
    PointEntity saved = captor.getValue();
    assertEquals(11L, saved.getXpos());
    assertEquals(22L, saved.getYpos());
    assertEquals("Copper", saved.getText());
  }

  @Test
  void shouldMapPointEntitiesToDomainDtos() {
    PointRepository pointRepository = mock(PointRepository.class);
    PointAdapter pointAdapter = new PointAdapter(pointRepository);
    UUID uuid = UUID.randomUUID();
    PointEntity entity = new PointEntity();
    entity.setUuid(uuid);
    entity.setXpos(500L);
    entity.setYpos(900L);
    entity.setText("Iron");
    when(pointRepository.findAll()).thenReturn(List.of(entity));

    Set<PointDto> result = pointAdapter.getAllPoints();

    assertEquals(1, result.size());
    PointDto dto = result.iterator().next();
    assertEquals(uuid, dto.getUuid());
    assertEquals(500L, dto.getXpos());
    assertEquals(900L, dto.getYpos());
    assertEquals("Iron", dto.getText());
    verify(pointRepository).findAll();
  }

  @Test
  void shouldDelegateDeleteToRepository() {
    PointRepository pointRepository = mock(PointRepository.class);
    PointAdapter pointAdapter = new PointAdapter(pointRepository);
    UUID uuid = UUID.randomUUID();

    pointAdapter.deletePoint(uuid);

    verify(pointRepository).deleteById(uuid);
  }
}
