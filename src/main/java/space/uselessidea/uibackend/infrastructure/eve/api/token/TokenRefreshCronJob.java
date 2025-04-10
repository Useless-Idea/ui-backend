package space.uselessidea.uibackend.infrastructure.eve.api.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.token.port.primary.TokenPrimaryPort;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenRefreshCronJob {

  private final TokenPrimaryPort tokenPrimaryPort;


  @Scheduled(cron = "${cronjob.tokenrefresh}")
  public void refreshAllTokens() {
    log.info("refreshAllTokens---START");
    tokenPrimaryPort.refreshAllTokens();
    log.info("refreshAllTokens---FINISH");
  }

}
