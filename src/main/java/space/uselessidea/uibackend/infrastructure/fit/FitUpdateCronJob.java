package space.uselessidea.uibackend.infrastructure.fit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.fit.port.FitPrimaryPort;

@Component
@RequiredArgsConstructor
@Slf4j
public class FitUpdateCronJob {

  private final FitPrimaryPort fitPrimaryPort;
  private final RabbitTemplate rabbitTemplate;
  private final Queue fitUpdateQueue;

  //Once per day
  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
  public void updateFits() {
    log.info("UpdateFits---START");
    fitPrimaryPort.getAllUuid().forEach(uuid -> {
      rabbitTemplate.convertAndSend(fitUpdateQueue.getName(), uuid);
    });
    log.info("UpdateFits---FINISH");
  }

}
