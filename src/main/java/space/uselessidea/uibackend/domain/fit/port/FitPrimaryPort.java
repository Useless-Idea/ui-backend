package space.uselessidea.uibackend.domain.fit.port;

import java.util.UUID;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;

public interface FitPrimaryPort {

  FitDto addFit(FitForm fitForm);

  FitDto updateFit(UUID fitUuid);
}
