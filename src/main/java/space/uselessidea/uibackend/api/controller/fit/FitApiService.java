package space.uselessidea.uibackend.api.controller.fit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;
import space.uselessidea.uibackend.domain.fit.port.FitPrimaryPort;

@Service
@RequiredArgsConstructor
public class FitApiService {

  private final FitPrimaryPort fitPrimaryPort;

  public FitDto addFit(FitForm fitForm) {
    return fitPrimaryPort.addFit(fitForm);
  }

}
