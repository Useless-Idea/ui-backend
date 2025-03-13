package space.uselessidea.uibackend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "eve-sso")
@Data
public class EveProperties {

  private String clientId;
  private String secret;
  private String callback;
  private String issuerUri;

}
