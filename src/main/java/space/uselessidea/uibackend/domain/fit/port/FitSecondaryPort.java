package space.uselessidea.uibackend.domain.fit.port;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.SearchFitDto;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilots;

public interface FitSecondaryPort {

  UUID saveFit(FitDto eft);

  Optional<Fit> updatePilotsList(UUID fitUuid, Pilots pilots);

  Optional<Fit> getFitByUuid(UUID fitUuid);

  Set<UUID> getAllUuid();

  Page<Fit> getFits(SearchFitDto searchFitDto);
}
