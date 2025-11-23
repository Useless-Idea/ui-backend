package space.uselessidea.uibackend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FeatureEnum {
  USER_SKILL("Skille gracza", Set.of(EveScopes.READ_SKILLS)),
  USER_STANDINGS("Standingi", Set.of(EveScopes.READ_STANDINGS)),
  OPEN_CONTRACTS("Kontrakty", Set.of(EveScopes.READ_CONTRACTS, EveScopes.OPEN_CONTRACT));

  private final String label;
  private final Set<String> scopes;

  public static Set<FeatureEnum> mapFromScpList(List<String> scpList) {
    return Arrays.stream(values())
        .filter(featureEnum -> new HashSet<>(scpList).containsAll(featureEnum.getScopes()))
        .collect(Collectors.toSet());

  }

  public String getName() {
    return name();
  }

}
