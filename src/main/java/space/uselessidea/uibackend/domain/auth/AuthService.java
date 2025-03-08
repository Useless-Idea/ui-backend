package space.uselessidea.uibackend.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.auth.dto.AuthMeResponse;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.infrastructure.api.eve.data.CorporationPublicData;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final EveApiPort eveApiPort;


  public AuthMeResponse getMe(CharacterPrincipal jwtUserToken) {
    Long corporationId = jwtUserToken.getCorpId();
    CorporationPublicData corporationPublicData = eveApiPort.getCorporationPublicData(corporationId);
    return AuthMeResponse.builder()
        .charId(jwtUserToken.getCharacterId())
        .charName(jwtUserToken.getCharName())
        .corpId(jwtUserToken.getCorpId())
        .corpName(corporationPublicData.getName())
        .roles(jwtUserToken.getRoles())
        .permission(jwtUserToken.getPermissions())
        .build();
  }


}
