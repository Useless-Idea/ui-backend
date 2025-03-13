package space.uselessidea.uibackend.api.config.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.domain.character.dto.CharactedData;
import space.uselessidea.uibackend.domain.character.port.secondary.CharacterSecondaryPort;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final EveApiPort eveApiPort;
  private final CharacterSecondaryPort characterPort;

  @Override
  public AbstractAuthenticationToken convert(Jwt source) {
    Long charId = Long.valueOf(source.getClaimAsString("sub").split(":")[2]);
    CharacterPublicData charPublicData = eveApiPort.getCharPublicData(charId);
    CharactedData characterData = characterPort.getCharacterData(
        charId);
    Set<String> userRole = characterData.getRoles();
    Set<String> userPermission = characterData.getPermission();
    List<SimpleGrantedAuthority> roles = userRole.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
        .toList();
    List<SimpleGrantedAuthority> permission = userPermission.stream()
        .map(role -> new SimpleGrantedAuthority(role.toUpperCase()))
        .toList();
    List<SimpleGrantedAuthority> sgaList = new ArrayList<>();
    sgaList.addAll(roles);
    sgaList.addAll(permission);

    return new JwtUserToken(source, charId, charPublicData.getName(), charPublicData.getCorporationId(), "", sgaList,
        userRole, userPermission, characterData.getIsBlocked());
  }
}
