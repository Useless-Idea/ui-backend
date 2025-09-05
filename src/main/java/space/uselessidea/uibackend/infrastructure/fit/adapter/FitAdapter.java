package space.uselessidea.uibackend.infrastructure.fit.adapter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.port.FitSecondaryPort;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilots;
import space.uselessidea.uibackend.infrastructure.fit.repository.FitRepository;

@Service
@RequiredArgsConstructor
public class FitAdapter implements FitSecondaryPort {

  private final FitRepository fitRepository;

  @Override
  public UUID saveFit(FitDto fitDto) {
    Fit fit = new Fit();
    fit.setEft(fitDto.getEft());
    fit.setShipId(fitDto.getShipId());
    fit = fitRepository.save(fit);
    return fit.getUuid();

  }

  @Override
  public Fit updatePilotsList(UUID fitUuid, Pilots pilots) {
    Fit fit = getFitByUuid(fitUuid);
    fit.setPilots(pilots);
    return fitRepository.save(fit);
  }

  @Override
  public Fit getFitByUuid(UUID fitUuid) {

    return fitRepository.getReferenceById(fitUuid);
  }

  @Override
  public Set<UUID> getAllUuid() {
    return fitRepository.findAll()
        .stream()
        .map(Fit::getUuid)
        .collect(Collectors.toSet());
  }
}
