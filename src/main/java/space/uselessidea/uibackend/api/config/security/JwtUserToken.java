package space.uselessidea.uibackend.api.config.security;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;

@Getter
public class JwtUserToken extends AbstractOAuth2TokenAuthenticationToken<Jwt> implements User {

  private final Long userId;
  private final Long corpId;
  private final String userName;
  private final String corpName;

  protected JwtUserToken(Jwt token, Collection<? extends GrantedAuthority> authorities) {
    super(token, authorities);
    this.userId = Long.valueOf(token.getClaimAsString("sub").split(":")[2]);
    this.userName = token.getClaimAsString("name");
  }

  @Override
  public Map<String, Object> getTokenAttributes() {
    return null;
  }

  @Override
  public Long getUserId() {
    return null;
  }

  @Override
  public String getCorpName() {
    return null;
  }

  @Override
  public String getUserName() {
    return null;
  }

  @Override
  public Long getCorpId() {
    return null;
  }
}
