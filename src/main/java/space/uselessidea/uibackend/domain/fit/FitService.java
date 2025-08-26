package space.uselessidea.uibackend.domain.fit;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.character.port.primary.CharacterPrimaryPort;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitDto.FitDtoBuilder;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;
import space.uselessidea.uibackend.domain.fit.port.FitPrimaryPort;
import space.uselessidea.uibackend.domain.fit.port.FitSecondaryPort;
import space.uselessidea.uibackend.domain.itemtype.port.PrimaryItemTypePort;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;

@Service
@RequiredArgsConstructor
public class FitService implements FitPrimaryPort {

  public static final Integer SHIP_NAME_INDEX = 0;
  public static final Integer FIT_NAME_INDEX = 1;

  private final PrimaryItemTypePort primaryItemTypePort;
  private final CharacterPrimaryPort characterPrimaryPort;
  private final FitSecondaryPort fitSecondaryPort;
  private final RabbitTemplate rabbitTemplate;
  private final Queue fitUpdateQueue;


  public FitDto addFit(FitForm fitForm) {
    FitDtoBuilder fitDto = fromEft(fitForm.getFit());
    UUID uuid = fitSecondaryPort.saveFit(fitForm.getFit());
    rabbitTemplate.convertAndSend(fitUpdateQueue.getName(), uuid);

    return fitDto
        .uuid(uuid)
        .build();
  }


  @Override
  @CachePut(value = "fits", key = "#uuid")
  @Transactional
  public FitDto getFitByUuid(UUID uuid) {
    Fit fit = fitSecondaryPort.getFitByUuid(uuid);

    String eft = fit.getEft();
    FitDto fitDto = fromEft(eft).uuid(fit.getUuid())
        .build();
    fit.setFitName(fitDto.getName());
    fit.setShipName(fitDto.getShipName());
    fit.setPilots(fit.getPilots());
    return fitDto;
  }


  @Override
  @Transactional
  public Set<UUID> getAllUuid() {
    return fitSecondaryPort.getAllUuid();
  }

  private FitDtoBuilder fromEft(String eft) {
    return FitDto.builder()
        .shipName(getShipName(eft).trim())
        .name(getFitName(eft).trim());
  }

  public String getFitName(String eft) {
    return getFirstRowData(eft)[FIT_NAME_INDEX];
  }

  public String getShipName(String eft) {
    return getFirstRowData(eft)[SHIP_NAME_INDEX];
  }

  private String[] getFirstRowData(String eft) {
    String firstRow = eft.split("\\r?\\n", 2)[0];
    firstRow = firstRow.substring(1, firstRow.length() - 1);
    return firstRow.split(",", 2);
  }

  public Set<String> getItemTypeNames(String eft) {
    return Arrays.stream(eft.split("\\r?\\n")).skip(1)
        .filter(row -> !row.isBlank())
        .map(row -> row.split("x[0-9]+")[0])
        .map(String::trim)
        .collect(Collectors.toSet());
  }
}
