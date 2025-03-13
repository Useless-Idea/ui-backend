package space.uselessidea.uibackend.domain;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeatureEnum {
  USER_SKILL(Set.of(EveScopes.READ_SKILLS));
  private final Set<String> scopes;

}
