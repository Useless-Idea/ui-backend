package space.uselessidea.uibackend.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeatureEnum {
  USER_SKILL(Set.of(EveScopes.READ_SKILLS)),
  USER_STANDINGS(Set.of(EveScopes.READ_STANDINGS));

  private final Set<String> scopes;

  public static Set<FeatureEnum> mapFromScpList(List<String> scpList) {
    return Arrays.stream(values())
        .filter(featureEnum -> new HashSet<>(scpList).containsAll(featureEnum.getScopes()))
        .collect(Collectors.toSet());

  }

}
