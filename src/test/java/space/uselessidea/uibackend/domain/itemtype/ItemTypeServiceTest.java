package space.uselessidea.uibackend.domain.itemtype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;
import space.uselessidea.uibackend.domain.itemtype.port.SecondaryItemTypePort;

class ItemTypeServiceTest {

  @Test
  void shouldSendAllItemTypeIdsToQueue() {
    EveApiPort eveApiPort = mock(EveApiPort.class);
    Queue queue = mock(Queue.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    SecondaryItemTypePort secondaryItemTypePort = mock(SecondaryItemTypePort.class);
    ItemTypeService itemTypeService =
        new ItemTypeService(eveApiPort, queue, rabbitTemplate, secondaryItemTypePort);

    when(eveApiPort.getAllItemTypeId()).thenReturn(Set.of(10L, 20L));
    when(queue.getName()).thenReturn("item-type-update");

    itemTypeService.updateItemTypes();

    verify(rabbitTemplate).convertAndSend("item-type-update", 10L);
    verify(rabbitTemplate).convertAndSend("item-type-update", 20L);
  }

  @Test
  void shouldSkipFetchingItemTypeWhenAlreadyStored() {
    EveApiPort eveApiPort = mock(EveApiPort.class);
    Queue queue = mock(Queue.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    SecondaryItemTypePort secondaryItemTypePort = mock(SecondaryItemTypePort.class);
    ItemTypeService itemTypeService =
        new ItemTypeService(eveApiPort, queue, rabbitTemplate, secondaryItemTypePort);

    when(secondaryItemTypePort.itemhasName(55L)).thenReturn(true);

    itemTypeService.updateItemType(55L);

    verify(eveApiPort, never()).getItemByItemTypeId(55L);
    verify(secondaryItemTypePort, never()).save(any(ItemTypeDto.class));
  }

  @Test
  void shouldFetchAndSaveItemTypeWhenMissingLocally() {
    EveApiPort eveApiPort = mock(EveApiPort.class);
    Queue queue = mock(Queue.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    SecondaryItemTypePort secondaryItemTypePort = mock(SecondaryItemTypePort.class);
    ItemTypeService itemTypeService =
        new ItemTypeService(eveApiPort, queue, rabbitTemplate, secondaryItemTypePort);

    ItemTypeDto itemTypeDto = ItemTypeDto.builder().itemId(42L).name("Rifter").build();
    when(secondaryItemTypePort.itemhasName(42L)).thenReturn(false);
    when(eveApiPort.getItemByItemTypeId(42L)).thenReturn(Optional.of(itemTypeDto));

    itemTypeService.updateItemType(42L);

    verify(secondaryItemTypePort).save(itemTypeDto);
  }

  @Test
  void shouldThrowWhenItemTypeNameIsUnknown() {
    EveApiPort eveApiPort = mock(EveApiPort.class);
    Queue queue = mock(Queue.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    SecondaryItemTypePort secondaryItemTypePort = mock(SecondaryItemTypePort.class);
    ItemTypeService itemTypeService =
        new ItemTypeService(eveApiPort, queue, rabbitTemplate, secondaryItemTypePort);

    when(secondaryItemTypePort.getIdByName("Unknown Item")).thenReturn(Optional.empty());

    ApplicationException exception =
        assertThrows(ApplicationException.class, () -> itemTypeService.getByName("Unknown Item"));

    assertEquals(ErrorCode.ITEM_TYPE_NOT_EXIST, exception.getErrorCode());
  }
}
