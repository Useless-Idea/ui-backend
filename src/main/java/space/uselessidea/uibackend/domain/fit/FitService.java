package space.uselessidea.uibackend.domain.fit;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.character.port.primary.CharacterPrimaryPort;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitDto.FitDtoBuilder;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;
import space.uselessidea.uibackend.domain.fit.port.FitPrimaryPort;
import space.uselessidea.uibackend.domain.fit.port.FitSecondaryPort;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;
import space.uselessidea.uibackend.domain.itemtype.port.PrimaryItemTypePort;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilot;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilots;

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
    FitDto fitDto = fromEft(fitForm.getFit()).build();
    ItemTypeDto ship = primaryItemTypePort.getByName(fitDto.getShipName()).orElseThrow();
    fitDto.setShipId(ship.getItemId());
    fitDto.setDescription(fitForm.getDescription());

    UUID uuid = fitSecondaryPort.saveFit(fitDto);
    rabbitTemplate.convertAndSend(fitUpdateQueue.getName(), uuid);
    fitDto.setUuid(uuid);
    return fitDto;
  }

  @Transactional
  public void updateFit(UUID fitUuid) {
    Fit fit = fitSecondaryPort.getFitByUuid(fitUuid);
    Set<String> itemList = getItemTypeNames(fit.getEft());
    itemList.add(fit.getShipName());
    Map<Long, Long> requiredSkillMap = itemList.stream()
        .map(primaryItemTypePort::getByName)
        .flatMap(Optional::stream)
        .map(ItemTypeDto::getRequiredSkillMap)
        .map(Map::entrySet)
        .flatMap(Set::stream)
        .collect(Collectors.toMap(
            Entry::getKey,
            Entry::getValue,
            Long::max
        ));
    List<Pilot> pilotsList = characterPrimaryPort.getCharacterIds().stream()
        .filter(characterId -> characterPrimaryPort.hasRequiredSkills(characterId, requiredSkillMap))
        .map(characterId -> characterPrimaryPort.getCharacterData(characterId, null))
        .map(characterData -> Pilot.builder().name(characterData.getCharacterName())
            .id(characterData.getCharacterId()).build())
        .toList();
    fitSecondaryPort.updatePilotsList(fitUuid, Pilots.builder()
        .active(pilotsList).build());
  }


  @Transactional
  public FitDto getFitByUuid(UUID uuid) {
    Fit fit = fitSecondaryPort.getFitByUuid(uuid);
    return FitDto.builder()
        .uuid(fit.getUuid())
        .name(fit.getFitName())
        .shipId(fit.getShipId())
        .shipName(fit.getShipName())
        .pilots(fit.getPilots())
        .build();
  }


  @Override
  @Transactional
  public Set<UUID> getAllUuid() {
    return fitSecondaryPort.getAllUuid();
  }

  private FitDtoBuilder fromEft(String eft) {
    return FitDto.builder()
        .shipName(getShipName(eft).trim())
        .eft(eft)
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
