package space.uselessidea.uibackend.api.controller.fit;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.api.controller.fit.dto.SimpleListFit;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;
import space.uselessidea.uibackend.domain.fit.dto.SearchFitDto;
import space.uselessidea.uibackend.domain.fit.port.FitPrimaryPort;

@Service
@RequiredArgsConstructor
public class FitApiService {

  private final FitPrimaryPort fitPrimaryPort;

  public FitDto addFit(FitForm fitForm) {
    return fitPrimaryPort.addFit(fitForm);
  }

  public Page<SimpleListFit> getFits(SearchFitDto searchFitDto) {
    return fitPrimaryPort.getFitBySearchFitDto(searchFitDto).map(SimpleListFit::fromFitDto);
  }

  public Map<String, Long> getShipNameIdMap() {
    return fitPrimaryPort.getShipNameIdMap();
  }
}
