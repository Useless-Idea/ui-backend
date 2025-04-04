package space.uselessidea.uibackend.infrastructure.itemtype.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import space.uselessidea.uibackend.infrastructure.itemtype.persistence.ItemType;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

  Optional<ItemType> findItemTypeByName(String name);

}
