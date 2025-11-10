package space.uselessidea.uibackend.infrastructure.fit.adapter;

import java.util.Optional;
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
    Fit fit = getFitByUuid(fitDto.getUuid()).orElse(new Fit());
    fit.setEft(fitDto.getEft());
    fit.setShipId(fitDto.getShipId());
    fit.setShipName(fitDto.getShipName());
    fit.setFitName(fitDto.getName());
    fit = fitRepository.save(fit);
    return fit.getUuid();

  }

  @Override
  public Optional<Fit> updatePilotsList(UUID fitUuid, Pilots pilots) {
    Optional<Fit> fitOpt = getFitByUuid(fitUuid);
    if (fitOpt.isEmpty()) {
      return Optional.empty();
    }
    Fit fit = fitOpt.get();
    fit.setPilots(pilots);
    return Optional.of(fitRepository.save(fit));
  }

  @Override
  public Optional<Fit> getFitByUuid(UUID fitUuid) {
    if (fitUuid == null) {
      return Optional.empty();
    }
    return fitRepository.findById(fitUuid);
  }

  @Override
  public Set<UUID> getAllUuid() {
    return fitRepository.findAll()
        .stream()
        .map(Fit::getUuid)
        .collect(Collectors.toSet());
  }
}
