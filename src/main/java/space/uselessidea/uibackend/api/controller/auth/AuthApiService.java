package space.uselessidea.uibackend.api.controller.auth;

import com.google.gson.Gson;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal;
import space.uselessidea.uibackend.domain.FeatureEnum;
import space.uselessidea.uibackend.domain.auth.AuthService;
import space.uselessidea.uibackend.domain.auth.dto.AuthMeResponse;
import space.uselessidea.uibackend.infrastructure.eve.auth.EveAuthAdapter;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;
import space.uselessidea.uibackend.properties.EveProperties;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthApiService {

  private final EveProperties eveProperties;
  private final AuthService authService;
  private final EveAuthAdapter eveAuthAdapter;
  private final RabbitTemplate rabbitTemplate;
  private final Queue tokenQueue;

  public AuthMeResponse getMe(CharacterPrincipal principal) {
    return authService.getMe(principal);
  }

  public String generateUrlForToken(Set<FeatureEnum> featureEnums) {
    String scopes = featureEnums.stream()
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
    TokenData token = eveAuthAdapter.handleCallback(code);

    Gson gson = new Gson();
    rabbitTemplate.convertAndSend(tokenQueue.getName(), gson.toJson(token));

    log.info(token.getAccessToken());
    log.info(token.getRefreshToken());

  }
}
