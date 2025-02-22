package space.uselessidea.uibackend.api.config.security;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

  @Value("${security.allowed-origins}")
  private String[] allowedOrigins;

  private static final String[] ALLOWED_HEADERS = {
      HttpHeaders.AUTHORIZATION,
      HttpHeaders.ACCEPT_LANGUAGE,
      HttpHeaders.CACHE_CONTROL,
      HttpHeaders.CONTENT_TYPE,
      HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
      HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
      HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS
  };

  private static final String[] ALLOWED_METHODS = {
      HttpMethod.GET.name(),
      HttpMethod.POST.name(),
      HttpMethod.PUT.name(),
      HttpMethod.DELETE.name(),
      HttpMethod.PATCH.name(),
      HttpMethod.OPTIONS.name()
  };

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration corsConfiguration = getCorsConfiguration();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }

  private CorsConfiguration getCorsConfiguration() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(List.of(ALLOWED_HEADERS));
    corsConfiguration.setAllowedMethods(List.of(ALLOWED_METHODS));
    corsConfiguration.setAllowedOrigins(getCorsAllowedOrigins());

    return corsConfiguration;
  }

  private List<String> getCorsAllowedOrigins() {
    return Arrays.stream(allowedOrigins)
        .map(String::trim)
        .toList();
  }
}
