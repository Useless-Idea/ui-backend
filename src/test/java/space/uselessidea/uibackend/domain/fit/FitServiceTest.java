package space.uselessidea.uibackend.domain.fit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageImpl;
import space.uselessidea.uibackend.domain.character.port.primary.CharacterPrimaryPort;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;
import space.uselessidea.uibackend.domain.fit.port.FitSecondaryPort;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;
import space.uselessidea.uibackend.domain.itemtype.port.PrimaryItemTypePort;

class FitServiceTest {

  @Test
  void shouldAddFitAndNormalizeDoctrines() {
    PrimaryItemTypePort primaryItemTypePort = mock(PrimaryItemTypePort.class);
    CharacterPrimaryPort characterPrimaryPort = mock(CharacterPrimaryPort.class);
    FitSecondaryPort fitSecondaryPort = mock(FitSecondaryPort.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    Queue fitUpdateQueue = mock(Queue.class);

    FitService fitService =
        new FitService(
            primaryItemTypePort,
            characterPrimaryPort,
            fitSecondaryPort,
            rabbitTemplate,
            fitUpdateQueue);

    FitForm fitForm = new FitForm();
    fitForm.setFit("[Rifter, Alpha Fit]\nDamage Control II x1");
    fitForm.setDescription("PVP fit");
    fitForm.setDoctrines(List.of(" Armor ", "", "Armor", "Shield"));

    when(primaryItemTypePort.getByName("Rifter"))
        .thenReturn(Optional.of(ItemTypeDto.builder().itemId(587L).name("Rifter").build()));
    UUID uuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
    when(fitSecondaryPort.saveFit(any(FitDto.class))).thenReturn(uuid);
    when(fitUpdateQueue.getName()).thenReturn("fit-update-queue");

    FitDto result = fitService.addFit(fitForm);

    ArgumentCaptor<FitDto> fitCaptor = ArgumentCaptor.forClass(FitDto.class);
    verify(fitSecondaryPort).saveFit(fitCaptor.capture());
    FitDto saved = fitCaptor.getValue();

    assertEquals("Alpha Fit", saved.getName());
    assertEquals("Rifter", saved.getShipName());
    assertEquals(587L, saved.getShipId());
    assertEquals("PVP fit", saved.getDescription());
    assertEquals(List.of("Armor", "Shield"), saved.getDoctrines());
    assertEquals(uuid, result.getUuid());
    verify(rabbitTemplate).convertAndSend("fit-update-queue", uuid);
  }

  @Test
  void shouldThrowWhenEditingMissingFit() {
    PrimaryItemTypePort primaryItemTypePort = mock(PrimaryItemTypePort.class);
    CharacterPrimaryPort characterPrimaryPort = mock(CharacterPrimaryPort.class);
    FitSecondaryPort fitSecondaryPort = mock(FitSecondaryPort.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    Queue fitUpdateQueue = mock(Queue.class);

    FitService fitService =
        new FitService(
            primaryItemTypePort,
            characterPrimaryPort,
            fitSecondaryPort,
            rabbitTemplate,
            fitUpdateQueue);

    UUID missingUuid = UUID.fromString("22222222-2222-2222-2222-222222222222");
    FitForm fitForm = new FitForm();
    fitForm.setFit("[Rifter, Updated Fit]\nDamage Control II x1");

    when(fitSecondaryPort.getFitByUuid(missingUuid)).thenReturn(Optional.empty());

    ApplicationException exception =
        assertThrows(ApplicationException.class, () -> fitService.editFit(missingUuid, fitForm));

    assertEquals(ErrorCode.FIT_NOT_EXIST, exception.getErrorCode());
  }

  @Test
  void shouldParseShipAndFitNameFromEftHeader() {
    PrimaryItemTypePort primaryItemTypePort = mock(PrimaryItemTypePort.class);
    CharacterPrimaryPort characterPrimaryPort = mock(CharacterPrimaryPort.class);
    FitSecondaryPort fitSecondaryPort = mock(FitSecondaryPort.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    Queue fitUpdateQueue = mock(Queue.class);

    FitService fitService =
        new FitService(
            primaryItemTypePort,
            characterPrimaryPort,
            fitSecondaryPort,
            rabbitTemplate,
            fitUpdateQueue);

    String eft = "[Orthrus, Kite Fit]\nDamage Control II x1";

    assertEquals("Orthrus", fitService.getShipName(eft));
    assertEquals(" Kite Fit", fitService.getFitName(eft));
  }

  @Test
  void shouldExtractUniqueItemTypeNamesFromEft() {
    PrimaryItemTypePort primaryItemTypePort = mock(PrimaryItemTypePort.class);
    CharacterPrimaryPort characterPrimaryPort = mock(CharacterPrimaryPort.class);
    FitSecondaryPort fitSecondaryPort = mock(FitSecondaryPort.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    Queue fitUpdateQueue = mock(Queue.class);

    FitService fitService =
        new FitService(
            primaryItemTypePort,
            characterPrimaryPort,
            fitSecondaryPort,
            rabbitTemplate,
            fitUpdateQueue);

    String eft =
        """
        [Drake, Heavy Fit]
        Ballistic Control System II x3

        Shield Power Relay II x2
        Scourge Fury Heavy Missile x2000
        """;

    Set<String> names = fitService.getItemTypeNames(eft);

    assertEquals(
        Set.of(
            "Ballistic Control System II", "Shield Power Relay II", "Scourge Fury Heavy Missile"),
        names);
  }

  @Test
  void shouldKeepFirstShipIdWhenShipNamesRepeat() {
    PrimaryItemTypePort primaryItemTypePort = mock(PrimaryItemTypePort.class);
    CharacterPrimaryPort characterPrimaryPort = mock(CharacterPrimaryPort.class);
    FitSecondaryPort fitSecondaryPort = mock(FitSecondaryPort.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    Queue fitUpdateQueue = mock(Queue.class);

    FitService fitService =
        new FitService(
            primaryItemTypePort,
            characterPrimaryPort,
            fitSecondaryPort,
            rabbitTemplate,
            fitUpdateQueue);

    FitDto first = FitDto.builder().shipName("Orthrus").shipId(17715L).build();
    FitDto duplicate = FitDto.builder().shipName("Orthrus").shipId(99999L).build();
    FitDto invalid = FitDto.builder().shipName(null).shipId(123L).build();

    when(fitSecondaryPort.getFits(any()))
        .thenReturn(new PageImpl<>(List.of(first, duplicate, invalid)));

    Map<String, Long> result = fitService.getShipNameIdMap();

    assertEquals(Map.of("Orthrus", 17715L), result);
  }
}
