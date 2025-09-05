package space.uselessidea.uibackend.domain.fit.port;

import java.util.Set;
import java.util.UUID;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;

public interface FitPrimaryPort {

  FitDto addFit(FitForm fitForm);

  FitDto getFitByUuid(UUID uuid);

  void updateFit(UUID fitUuid);

  Set<UUID> getAllUuid();
}
