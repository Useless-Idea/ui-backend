package space.uselessidea.uibackend.domain.fit.port;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.FitForm;
import space.uselessidea.uibackend.domain.fit.dto.SearchFitDto;

public interface FitPrimaryPort {

  FitDto addFit(FitForm fitForm);

  FitDto editFit(UUID fitUuid, FitForm fitForm);

  FitDto getFitByUuid(UUID uuid);

  void deleteFit(UUID fitUuid);

  void updateFit(UUID fitUuid);

  Set<UUID> getAllUuid();

  Page<FitDto> getFitBySearchFitDto(SearchFitDto searchFitDto);

  Map<String, Long> getShipNameIdMap();

  List<String> getDoctrines();

  void refreshDoctrinesCache();
}
