package space.uselessidea.uibackend.infrastructure.eve.api.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.uselessidea.uibackend.infrastructure.eve.api.token.persistence.EsiToken;

@Repository
public interface EsiTokenRepository extends JpaRepository<EsiToken, Long> {

}
