package space.uselessidea.uibackend.domain.itemtype.port;

import java.util.Optional;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;

public interface PrimaryItemTypePort {

  void updateItemTypes();

  void updateItemType(Long itemTypeId);

  Optional<ItemTypeDto> getById(Long itemTypeId);

  Optional<ItemTypeDto> getByName(String name);
}
