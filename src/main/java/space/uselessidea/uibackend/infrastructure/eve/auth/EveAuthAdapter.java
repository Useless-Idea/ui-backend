package space.uselessidea.uibackend.infrastructure.eve.auth;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import space.uselessidea.uibackend.domain.token.port.secondary.EveAuthSecondaryPort;
import space.uselessidea.uibackend.infrastructure.eve.auth.data.TokenData;
import space.uselessidea.uibackend.properties.EveProperties;

@Service
@RequiredArgsConstructor
public class EveAuthAdapter implements EveAuthSecondaryPort {

  private final EveProperties eveProperties;


  private static final String TOKEN_URL = "https://login.eveonline.com/v2/oauth/token";
  private static final String VERIFY_TOKEN_URL = "https://esi.evetech.net/verify";

  @Override
  public boolean verifyToken(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + token);
    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request =
        new HttpEntity<String>(headers);
    try {
      restTemplate.exchange(VERIFY_TOKEN_URL, HttpMethod.GET, request, String.class);
    } catch (HttpClientErrorException e) {
      return false;
    }
    return true;

  }

  @Override
  public TokenData refreshToken(String refreshToken) {
    String form = "grant_type=refresh_token&refresh_token=" + refreshToken;

    return getTokenDataByForm(form);
  }

  private TokenData getTokenDataByForm(String form) {
    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request =
        new HttpEntity<String>(form, createHeaders());
    return restTemplate.postForObject(TOKEN_URL, request, TokenData.class);

  }

  @Override
  public TokenData handleCallback(String code) {
    String form = "grant_type=authorization_code&code=" + code;
    return getTokenDataByForm(form);

  }

  private HttpHeaders createHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.set("Authorization", "Basic " + getEncodedAuth());
    headers.set("Host", "login.eveonline.com");
    return headers;
  }

  private String getEncodedAuth() {
    String originalInput = eveProperties.getClientId() + ":" + eveProperties.getSecret();
    return Base64.getEncoder().encodeToString(originalInput.getBytes());
  }

}
