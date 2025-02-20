package space.uselessidea.uibackend.api.config.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final SecurityErrorHandler customErrorHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(
            AbstractHttpConfigurer::disable
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                antMatcher("/api/v1/test"),
                antMatcher("/v3/api-docs/**"),
                antMatcher("/swagger-ui/**"),
                antMatcher("/swagger-ui.html"),
                antMatcher("/login/**"),
                antMatcher("/login"),

                antMatcher(HttpMethod.OPTIONS, "/**")
            ).permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            .accessDeniedHandler(customErrorHandler)
            .authenticationEntryPoint(customErrorHandler)
        )
        .sessionManagement(
            session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
    ;
    return http.build();
  }

  @Bean
  public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(new CustomJwtGrantedAuthoritiesConverter());
    return converter;
  }

}
