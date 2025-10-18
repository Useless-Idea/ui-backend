package space.uselessidea.uibackend.infrastructure.pax_dei.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import space.uselessidea.uibackend.infrastructure.pax_dei.persistence.PointEntity;

public interface PointRepository extends JpaRepository<PointEntity, UUID> {

}
