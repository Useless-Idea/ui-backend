package space.uselessidea.uibackend.infrastructure.fit.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;

@Repository
public interface FitRepository extends JpaRepository<Fit, UUID> {

}
