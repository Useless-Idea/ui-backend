package space.uselessidea.uibackend.infrastructure.character.adapter;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;
import space.uselessidea.uibackend.domain.character.port.secondrt.CharacterPort;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.infrastructure.api.eve.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.api.eve.data.CorporationPublicData;
import space.uselessidea.uibackend.infrastructure.character.persistence.Character;
import space.uselessidea.uibackend.infrastructure.character.persistence.Corporation;
import space.uselessidea.uibackend.infrastructure.character.persistence.Permission;
import space.uselessidea.uibackend.infrastructure.character.persistence.Role;
import space.uselessidea.uibackend.infrastructure.character.repository.CharacterRepository;
import space.uselessidea.uibackend.infrastructure.character.repository.CorporationRepository;

@Service
@RequiredArgsConstructor
public class CharacterAdapter implements CharacterPort {

  private final CharacterRepository characterRepository;
  private final EveApiPort eveApiPort;
  private final CorporationRepository corporationRepository;

  @Override
  @Transactional
  public CharactedData getCharacterData(Long id) {
    Character character = characterRepository.findById(
        id).orElseGet(() -> createCharacter(id));

    return map(character);
  }

  private Character createCharacter(Long characterId) {
    CharacterPublicData characterPublicData = eveApiPort.getCharPublicData(
        characterId);
    Corporation corporation = getCorporation(characterPublicData.getCorporationId());
    Character character = new Character();
    character.setId(characterId);
    character.setName(characterPublicData.getName());
    character.setCorporation(corporation);
    character.setIsBlock(corporation.getIsBlocked());

    return characterRepository.save(character);
  }

  public Corporation getCorporation(Long corporationId) {
    return corporationRepository.findById(corporationId)
        .orElseGet(() -> createCorporation(corporationId));
  }

  private Corporation createCorporation(Long corporationId) {
    CorporationPublicData corporationPublicData = eveApiPort.getCorporationPublicData(corporationId);
    Corporation corporation = new Corporation();
    corporation.setName(corporationPublicData.getName());
    corporation.setId(corporationId);

    return corporationRepository.save(corporation);
  }

  private CharactedData map(Character character) {
    return CharactedData.builder()
        .characterName(character.getName())
        .characterId(character.getId())
        .isBlocked(character.getIsBlock())
        .roles(character.getRoles().stream()
            .map(Role::getCode)
            .collect(Collectors.toSet()))
        .permission(character.getRoles().stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(Permission::getCode)
            .collect(Collectors.toSet()))
        .build();
  }
}
