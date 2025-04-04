package space.uselessidea.uibackend.infrastructure.itemtype.adapter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;
import space.uselessidea.uibackend.domain.itemtype.port.SecondaryItemTypePort;
import space.uselessidea.uibackend.infrastructure.itemtype.persistence.ItemType;
import space.uselessidea.uibackend.infrastructure.itemtype.repository.ItemTypeRepository;

@Service
@RequiredArgsConstructor
public class ItemTypeAdapter implements SecondaryItemTypePort {

  private final ItemTypeRepository itemTypeRepository;

  @Override
  public ItemTypeDto save(ItemTypeDto itemTypeDto) {
    ItemType itemType = new ItemType();
    itemType.setId(itemTypeDto.getItemId());
    itemType.setName(itemTypeDto.getName());
    itemTypeRepository.save(itemType);
    return itemTypeDto;
  }

  @Override
  public boolean itemhasName(Long itemTypeId) {
    return itemTypeRepository.findById(
            itemTypeId)
        .filter(itemType -> itemType.getName() != null && !itemType.getName().isEmpty())
        .isPresent();
  }

  @Override
  public Optional<Long> getIdByName(String name) {
    return itemTypeRepository.findItemTypeByName(name).map(ItemType::getId);
  }
}
