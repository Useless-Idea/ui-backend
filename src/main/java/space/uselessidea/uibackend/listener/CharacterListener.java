package space.uselessidea.uibackend.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.api.config.rabbit.RabbitMqConfig;
import space.uselessidea.uibackend.domain.character.port.primary.CharacterPrimaryPort;

@Component
@RequiredArgsConstructor
@Slf4j
public class CharacterListener {

  private final CharacterPrimaryPort characterPrimaryPort;

  @RabbitListener(queues = {RabbitMqConfig.CHAR_UPDATE_QUEUE})
  private void handleNewToken(Long characterId) {
    characterPrimaryPort.updateCharacterData(characterId);
  }

}
