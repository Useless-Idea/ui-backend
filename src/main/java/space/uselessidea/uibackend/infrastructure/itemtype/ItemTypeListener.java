package space.uselessidea.uibackend.infrastructure.itemtype;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.api.config.rabbit.RabbitMqConfig;
import space.uselessidea.uibackend.domain.itemtype.port.PrimaryItemTypePort;

@Component
@RequiredArgsConstructor

@Slf4j
public class ItemTypeListener {

  private final PrimaryItemTypePort primaryItemTypePort;

  @RabbitListener(queues = {RabbitMqConfig.ITEM_TYPE_QUEUE})
  private void handleNewToken(Long itemTypeId) {
    primaryItemTypePort.updateItemType(itemTypeId);
  }


}
