package space.uselessidea.uibackend.api.config.security;

import java.security.Principal;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public interface CharacterPrincipal extends Principal {

  Long getCharacterId();

  String getCorpName();

  String getCharName();

  Long getCorpId();

  default String getJwtAccessToken() {
    return null;
  }

  Collection<GrantedAuthority> getAuthorities();
}
