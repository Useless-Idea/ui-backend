package space.uselessidea.uibackend.infrastructure.fit;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.api.config.rabbit.RabbitMqConfig;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.fit.port.FitPrimaryPort;

@Component
@RequiredArgsConstructor

@Slf4j
public class FitListener {

  private final FitPrimaryPort fitPrimaryPort;


  @RabbitListener(queues = {RabbitMqConfig.FIT_UPDATE_QUEUE})
  public void fitUpdate(UUID fitUuid) {
    try {
      fitPrimaryPort.updateFit(fitUuid);
    } catch (ApplicationException e) {
      log.error("Błąd podczas aktualizacji fitu [{}]", fitUuid, e);
      if (ErrorCode.FIT_NOT_EXIST == e.getErrorCode()) {
        log.error("Fit [{}] nie istnieje", fitUuid);
      } else {
        throw e;
      }

    }
  }

}
