package space.uselessidea.uibackend.api.config.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final SecurityErrorHandler customErrorHandler;
  private final CustomAuthenticationTokenConverter customAuthenticationTokenConverter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(
            AbstractHttpConfigurer::disable
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                antMatcher("/api/v1/test"),
                antMatcher("/api/v1/auth/login"),
                antMatcher("/api/v1/auth/callback"),
                antMatcher("/api/v1/skill/map"),
                antMatcher("/v3/api-docs/**"),
                antMatcher("/swagger-ui/**"),
                antMatcher("/swagger-ui.html"),
                antMatcher(HttpMethod.OPTIONS, "/**")
            ).permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(customAuthenticationTokenConverter))
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


}
