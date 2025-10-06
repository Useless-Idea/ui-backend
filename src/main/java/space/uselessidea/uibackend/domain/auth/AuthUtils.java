package space.uselessidea.uibackend.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.properties.EveProperties;

@Component
@RequiredArgsConstructor
public class AuthUtils {

  private final EveProperties eveProperties;

  public Jwt convertAccessTokenToJwt(String accessToken) {
    JwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(eveProperties.getIssuerUri()).build();
    Jwt jwt = jwtDecoder.decode(accessToken);
    return jwt;
  }

  public Long getSubFromJwtToken(Jwt jwt) {
    return Long.valueOf(jwt.getClaimAsString("sub").split(":")[2]);
  }

}
