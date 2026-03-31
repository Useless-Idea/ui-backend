package space.uselessidea.uibackend.infrastructure.fit.adapter;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.domain.fit.dto.PilotDto;
import space.uselessidea.uibackend.domain.fit.dto.PilotsDto;
import space.uselessidea.uibackend.domain.fit.dto.SearchFitDto;
import space.uselessidea.uibackend.domain.fit.port.FitSecondaryPort;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilot;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Pilots;
import space.uselessidea.uibackend.infrastructure.fit.repository.FitRepository;

@Service
@RequiredArgsConstructor
public class FitAdapter implements FitSecondaryPort {

  private final FitRepository fitRepository;

  @Override
  public UUID saveFit(FitDto fitDto) {
    Fit fit = getFitEntityByUuid(fitDto.getUuid()).orElse(new Fit());
    fit.setEft(fitDto.getEft());
    fit.setShipId(fitDto.getShipId());
    fit.setShipName(fitDto.getShipName());
    fit.setFitName(fitDto.getName());
    fit.setDoctrines(fitDto.getDoctrines() == null ? java.util.List.of() : fitDto.getDoctrines());
    return fitRepository.save(fit).getUuid();
  }

  @Override
  public Optional<FitDto> updatePilotsList(UUID fitUuid, PilotsDto pilots) {
    Optional<Fit> fitOpt = getFitEntityByUuid(fitUuid);
    if (fitOpt.isEmpty()) {
      return Optional.empty();
    }
    Fit fit = fitOpt.get();
    fit.setPilots(mapPilotsToEntity(pilots));
    return Optional.of(mapToDto(fitRepository.save(fit)));
  }

  @Override
  public Optional<FitDto> getFitByUuid(UUID fitUuid) {
    return getFitEntityByUuid(fitUuid).map(this::mapToDto);
  }

  private Optional<Fit> getFitEntityByUuid(UUID fitUuid) {
    if (fitUuid == null) {
      return Optional.empty();
    }
    return fitRepository.findById(fitUuid);
  }

  @Override
  public void deleteFit(UUID fitUuid) {
    fitRepository.deleteById(fitUuid);
  }

  @Override
  public Set<UUID> getAllUuid() {
    return fitRepository.findAll().stream().map(Fit::getUuid).collect(Collectors.toSet());
  }

  @Override
  public Page<FitDto> getFits(SearchFitDto searchFitDto) {
    if (searchFitDto == null) {
      searchFitDto = SearchFitDto.builder().build();
    }
    searchFitDto.normalizeLists();
    return fitRepository
        .findFits(
            searchFitDto.getFitName(),
            searchFitDto.getPilots(),
            searchFitDto.getDoctrines(),
            searchFitDto.toPageable())
        .map(this::mapToDto);
  }

  @Override
  public Set<String> getAllDoctrines() {
    return fitRepository.findAll().stream()
        .map(Fit::getDoctrines)
        .filter(doctrines -> doctrines != null && !doctrines.isEmpty())
        .flatMap(java.util.Collection::stream)
        .filter(doctrine -> doctrine != null && !doctrine.isBlank())
        .map(String::trim)
        .collect(Collectors.toSet());
  }

  private FitDto mapToDto(Fit fit) {
    return FitDto.builder()
        .uuid(fit.getUuid())
        .name(fit.getFitName())
        .shipId(fit.getShipId())
        .shipName(fit.getShipName())
        .eft(fit.getEft())
        .pilots(mapPilotsToDto(fit.getPilots()))
        .doctrines(fit.getDoctrines() == null ? java.util.List.of() : fit.getDoctrines())
        .build();
  }

  private Pilots mapPilotsToEntity(PilotsDto pilotsDto) {
    if (pilotsDto == null) {
      return null;
    }
    return Pilots.builder()
        .active(
            pilotsDto.getActive() == null
                ? java.util.List.of()
                : pilotsDto.getActive().stream().map(this::mapPilotToEntity).toList())
        .inactive(
            pilotsDto.getInactive() == null
                ? java.util.List.of()
                : pilotsDto.getInactive().stream().map(this::mapPilotToEntity).toList())
        .build();
  }

  private PilotsDto mapPilotsToDto(Pilots pilots) {
    if (pilots == null) {
      return null;
    }
    return PilotsDto.builder()
        .active(
            pilots.getActive() == null
                ? java.util.List.of()
                : pilots.getActive().stream().map(this::mapPilotToDto).toList())
        .inactive(
            pilots.getInactive() == null
                ? java.util.List.of()
                : pilots.getInactive().stream().map(this::mapPilotToDto).toList())
        .build();
  }

  private Pilot mapPilotToEntity(PilotDto pilotDto) {
    return Pilot.builder().id(pilotDto.getId()).name(pilotDto.getName()).build();
  }

  private PilotDto mapPilotToDto(Pilot pilot) {
    return PilotDto.builder().id(pilot.getId()).name(pilot.getName()).build();
  }
}
