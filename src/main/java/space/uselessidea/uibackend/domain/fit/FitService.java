package space.uselessidea.uibackend.domain.fit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
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
  private final FitSecondaryPort fitSecondaryPort;
  private final RabbitTemplate rabbitTemplate;
  private final Queue fitUpdateQueue;


  public FitDto addFit(FitForm fitForm) {
    FitDtoBuilder fitDto = fromEft(fitForm.getDescription());
    UUID uuid = fitSecondaryPort.saveFit(fitForm.getDescription());
    rabbitTemplate.convertAndSend(fitUpdateQueue.getName(), uuid);

    return fitDto
        .uuid(uuid)
        .build();
  }

  @Override
  @CachePut(value = "fit", key = "fitUuid")
  public FitDto updateFit(UUID fitUuid) {
    Fit fit = fitSecondaryPort.getFitByUuid(fitUuid);
    String eft = fit.getEft();
    FitDtoBuilder fitDto = fromEft(eft);

    return fitDto
        .uuid(fit.getUuid())
        .build();
  }

  private FitDtoBuilder fromEft(String eft) {
    Set<String> itemNameSet = new HashSet<>(getItemTypeNames(eft));
    itemNameSet.add(getShipName(eft));
    Map<Long, Long> requiredSkills = getRequiredSkills(itemNameSet);

    return FitDto.builder()
        .shipName(getShipName(eft))
        .name(getFitName(eft))
        .requiredSkills(requiredSkills);
  }

  public String getFitName(String eft) {
    return getFirstRowData(eft)[FIT_NAME_INDEX];
  }

  public String getShipName(String eft) {
    return getFirstRowData(eft)[SHIP_NAME_INDEX];
  }

  private String[] getFirstRowData(String eft) {
    String firstRow = eft.split("\r?\n|\r", 2)[0];
    firstRow = firstRow.substring(1, firstRow.length() - 1);
    return firstRow.split(",", 2);
  }

  public Set<String> getItemTypeNames(String eft) {
    return Arrays.stream(eft.split("\r?\n|\r")).skip(1)
        .filter(String::isBlank)
        .map(row -> row.split("x[0-9]+")[0])
        .map(String::trim)
        .collect(Collectors.toSet());
  }


  private Map<Long, Long> getRequiredSkills(Set<String> itemNameSet) {
    return itemNameSet.stream()
        .map(itemName -> primaryItemTypePort.getByName(itemName).orElseThrow(() -> new ApplicationException(
            ErrorCode.ITEM_TYPE_NOT_EXIST)))
        .map(itemTypeDto -> itemTypeDto.getRequiredSkillMap().entrySet())
        .flatMap(Collection::stream)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::max));


  }

}
