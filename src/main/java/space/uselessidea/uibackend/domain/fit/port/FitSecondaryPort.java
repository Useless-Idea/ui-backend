package space.uselessidea.uibackend.domain.fit.port;

import java.util.UUID;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;

public interface FitSecondaryPort {

  UUID saveFit(String eft);

  Fit getFitByUuid(UUID fitUuid);
}
