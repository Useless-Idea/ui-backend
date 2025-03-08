package space.uselessidea.uibackend.api.config.security;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

public interface CharacterPrincipal extends Principal {

  Long getCharacterId();

  String getCorpName();

  String getCharName();

  Long getCorpId();

  default String getJwtAccessToken() {
    return null;
  }

  Set<String> getRoles();

  Set<String> getPermissions();

  Collection<GrantedAuthority> getAuthorities();
}
