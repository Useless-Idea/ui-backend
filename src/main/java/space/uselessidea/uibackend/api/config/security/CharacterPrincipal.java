package space.uselessidea.uibackend.api.config.security;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public interface CharacterPrincipal {

  Long getCharacterId();

  String getCorpName();

  String getCharName();

  Long getCorpId();

  default String getJwtAccessToken() {
    return null;
  }

  Collection<GrantedAuthority> getAuthorities();
}
