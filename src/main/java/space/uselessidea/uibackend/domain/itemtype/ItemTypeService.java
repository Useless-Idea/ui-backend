package space.uselessidea.uibackend.domain.itemtype;

import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;
import space.uselessidea.uibackend.domain.itemtype.port.PrimaryItemTypePort;
import space.uselessidea.uibackend.domain.itemtype.port.SecondaryItemTypePort;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemTypeService implements PrimaryItemTypePort {

  private final EveApiPort eveApiPort;
  private final Queue itemTypeQueue;
  private final RabbitTemplate rabbitTemplate;
  private final SecondaryItemTypePort secondaryItemTypePort;

  @Override
  public void updateItemTypes() {
    Set<Long> set = eveApiPort.getAllItemTypeId();
    set.forEach(itemTypeId -> {
      log.info(String.format("""
          Try to add %d to Queue
          """, itemTypeId));
      rabbitTemplate.convertAndSend(itemTypeQueue.getName(), itemTypeId);
      log.info(String.format("""
          Added %d to Queue
          """, itemTypeId));
    });
  }

  @Override
  public void updateItemType(Long itemTypeId) {
    if (!secondaryItemTypePort.itemhasName(itemTypeId)) {
      Optional<ItemTypeDto> itemTypeDto = eveApiPort.getItemByItemTypeId(itemTypeId);
      itemTypeDto.ifPresent(secondaryItemTypePort::save);
    }

  }

  @Override
  public Optional<ItemTypeDto> getById(Long itemTypeId) {
    return eveApiPort.getItemByItemTypeId(itemTypeId);
  }

  @Override
  public Optional<ItemTypeDto> getByName(String name) {
    Long id = secondaryItemTypePort.getIdByName(name)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ITEM_TYPE_NOT_EXIST, name));

    return getById(id);
  }
}
