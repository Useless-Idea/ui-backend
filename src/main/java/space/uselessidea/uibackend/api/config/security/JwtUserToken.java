package space.uselessidea.uibackend.api.config.security;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;

@Getter
public class JwtUserToken extends AbstractOAuth2TokenAuthenticationToken<Jwt> implements CharacterPrincipal {

  private final Long charId;
  private final Long corpId;
  private final String username;
  private final String corpName;

  protected JwtUserToken(Jwt token, Long charId, String username, Long corpId, String corpName,
      Collection<? extends GrantedAuthority> authorities) {
    super(token, authorities);
    this.setAuthenticated(true);
    this.charId = charId;
    this.username = username;
    this.corpId = corpId;
    this.corpName = corpName;
  }

  @Override
  public Map<String, Object> getTokenAttributes() {
    return this.getToken().getClaims();
  }

  @Override
  public Long getCharacterId() {
    return charId;
  }

  @Override
  public String getCorpName() {
    return null;
  }

  @Override
  public String getCharName() {
    return username;
  }

  @Override
  public Long getCorpId() {
    return corpId;
  }
}
