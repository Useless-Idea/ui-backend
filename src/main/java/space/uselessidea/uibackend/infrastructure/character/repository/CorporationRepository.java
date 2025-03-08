package space.uselessidea.uibackend.infrastructure.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.uselessidea.uibackend.infrastructure.character.persistence.Corporation;

@Repository
public interface CorporationRepository extends JpaRepository<Corporation, Long> {

}
