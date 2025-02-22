package space.uselessidea.uibackend.api.config.security;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.infrastructure.api.eve.data.CharacterPublicData;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final EveApiPort eveApiPort;

  @Override
  public AbstractAuthenticationToken convert(Jwt source) {
    Long charId = Long.valueOf(source.getClaimAsString("sub").split(":")[2]);
    CharacterPublicData charPublicData = eveApiPort.getCharPublicData(charId);
    List<String> testRoles = List.of("role1", "role2");
    List<SimpleGrantedAuthority> roles = testRoles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
        .collect(Collectors.toList());

    return new JwtUserToken(source, charId, charPublicData.getName(), charPublicData.getCorporationId(), "", roles
    );
  }
}
