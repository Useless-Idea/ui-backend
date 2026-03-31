package space.uselessidea.uibackend.api.controller.auth;

import com.google.gson.Gson;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import space.uselessidea.uibackend.api.config.rabbit.ScopeUserDto;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.FeatureEnum;
import space.uselessidea.uibackend.domain.auth.AuthService;
import space.uselessidea.uibackend.domain.auth.AuthUtils;
import space.uselessidea.uibackend.domain.auth.dto.AuthMeResponse;
import space.uselessidea.uibackend.domain.security.UserContext;
import space.uselessidea.uibackend.domain.token.dto.TokenDataDto;
import space.uselessidea.uibackend.domain.token.port.secondary.EveAuthSecondaryPort;
import space.uselessidea.uibackend.properties.EveProperties;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthApiService {

  private final EveProperties eveProperties;
  private final AuthService authService;
  private final EveAuthSecondaryPort eveAuthSecondaryPort;
  private final RabbitTemplate rabbitTemplate;
  private final Queue tokenQueue;
  private final Queue stateIdQueue;
  private final AuthUtils authUtils;

  public AuthMeResponse getMe(CharacterPrincipal principal) {
    return authService.getMe(toUserContext(principal));
  }

  public String generateUrlForToken(Set<FeatureEnum> featureEnums) {
    String scopes =
        featureEnums.stream()
            .flatMap(featureEnum -> featureEnum.getScopes().stream())
            .collect(Collectors.joining(" "));
    return UriComponentsBuilder.fromUriString("https://login.eveonline.com/v2/oauth/authorize")
        .queryParam("response_type", "code")
        .queryParam("redirect_uri", eveProperties.getCallback())
        .queryParam("client_id", eveProperties.getClientId())
        .queryParam("scope", scopes)
        .queryParam("state", UUID.randomUUID().toString())
        .toUriString();
  }

  public void handleCallback(String code, String state) {
    TokenDataDto token = eveAuthSecondaryPort.handleCallback(code);

    Gson gson = new Gson();
    rabbitTemplate.convertAndSend(tokenQueue.getName(), gson.toJson(token));

    Jwt jwt = authUtils.convertAccessTokenToJwt(token.getAccessToken());
    long userId = authUtils.getSubFromJwtToken(jwt);

    rabbitTemplate.convertAndSend(
        stateIdQueue.getName(),
        gson.toJson(ScopeUserDto.builder().id(userId).state(state).build()));

    log.info(token.getAccessToken());
    log.info(token.getRefreshToken());
  }

  private UserContext toUserContext(CharacterPrincipal principal) {
    if (principal == null) {
      return null;
    }
    return UserContext.builder()
        .characterId(principal.getCharacterId())
        .charName(principal.getCharName())
        .corpId(principal.getCorpId())
        .roles(principal.getRoles())
        .permissions(principal.getPermissions())
        .build();
  }
}
