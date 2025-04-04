package space.uselessidea.uibackend.domain.itemtype.port;

import java.util.Optional;
import space.uselessidea.uibackend.domain.itemtype.dto.ItemTypeDto;

public interface SecondaryItemTypePort {

  ItemTypeDto save(ItemTypeDto itemTypeDto);


  boolean itemhasName(Long itemTypeId);

  Optional<Long> getIdByName(String name);
}
