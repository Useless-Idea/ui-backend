package space.uselessidea.uibackend.domain.fit.port;

import java.util.Set;
import java.util.UUID;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilots;

public interface FitSecondaryPort {

  UUID saveFit(FitDto eft);

  Fit updatePilotsList(UUID fitUuid, Pilots pilots);

  Fit getFitByUuid(UUID fitUuid);

  Set<UUID> getAllUuid();
}
