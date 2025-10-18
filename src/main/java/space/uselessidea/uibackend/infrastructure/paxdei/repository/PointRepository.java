package space.uselessidea.uibackend.infrastructure.paxdei.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import space.uselessidea.uibackend.infrastructure.paxdei.persistence.PointEntity;

public interface PointRepository extends JpaRepository<PointEntity, UUID> {

}
