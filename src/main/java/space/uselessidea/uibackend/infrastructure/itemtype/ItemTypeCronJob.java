package space.uselessidea.uibackend.infrastructure.itemtype;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.itemtype.port.PrimaryItemTypePort;

@Component
@Slf4j
@RequiredArgsConstructor
public class ItemTypeCronJob {

  private final PrimaryItemTypePort primaryItemTypePort;


  //Once per day
  //@Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
  public void updateItemTypes() {
    log.info("UpdateItemTypes---START");
    primaryItemTypePort.updateItemTypes();
    log.info("UpdateItemTypes---FINISH");
  }

}
