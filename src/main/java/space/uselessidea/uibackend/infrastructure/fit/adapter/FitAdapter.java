package space.uselessidea.uibackend.infrastructure.fit.adapter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.fit.port.FitSecondaryPort;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;
import space.uselessidea.uibackend.infrastructure.fit.repository.FitRepository;

@Service
@RequiredArgsConstructor
public class FitAdapter implements FitSecondaryPort {

  private final FitRepository fitRepository;

  @Override
  public UUID saveFit(String eft) {
    Fit fit = new Fit();
    fit.setEft(eft);
    fit = fitRepository.save(fit);
    return fit.getUuid();

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
