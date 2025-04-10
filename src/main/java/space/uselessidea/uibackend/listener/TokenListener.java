package space.uselessidea.uibackend.listener;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.api.config.rabbit.RabbitMqConfig;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.token.port.primary.TokenPrimaryPort;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenListener {

  private final TokenPrimaryPort tokenPrimaryPort;

  @RabbitListener(queues = {RabbitMqConfig.TOKEN_QUEUE})
  private void handleNewToken(String token) {
    Gson gson = new Gson();
    TokenData tokenData = gson.fromJson(token, TokenData.class);
    Long charId;
    try {
      charId = tokenPrimaryPort.addToken(tokenData);
    } catch (ApplicationException applicationException) {
      log.error(String.format("Token [%s] is not valid", tokenData.getAccessToken()));
    }

  }

}
