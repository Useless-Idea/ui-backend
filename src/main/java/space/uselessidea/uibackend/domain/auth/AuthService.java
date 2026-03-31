package space.uselessidea.uibackend.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.auth.dto.AuthMeResponse;
import space.uselessidea.uibackend.domain.eve.api.dto.CorporationPublicDataDto;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;
import space.uselessidea.uibackend.domain.security.UserContext;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final EveApiPort eveApiPort;

  public AuthMeResponse getMe(UserContext jwtUserToken) {
    Long corporationId = jwtUserToken.getCorpId();
    CorporationPublicDataDto corporationPublicData =
        eveApiPort.getCorporationPublicData(corporationId);
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
