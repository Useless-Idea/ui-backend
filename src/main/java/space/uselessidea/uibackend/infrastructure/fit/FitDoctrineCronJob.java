package space.uselessidea.uibackend.infrastructure.fit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.fit.port.FitPrimaryPort;

@Component
@RequiredArgsConstructor
@Slf4j
public class FitDoctrineCronJob {

  private final FitPrimaryPort fitPrimaryPort;

  // Once per day
  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
  public void refreshDoctrines() {
    log.info("RefreshFitDoctrines---START");
    try {
      fitPrimaryPort.refreshDoctrinesCache();
      log.info("RefreshFitDoctrines---FINISH");
    } catch (Exception ex) {
      log.error("RefreshFitDoctrines---FAILED", ex);
    }
  }
}
