package space.uselessidea.uibackend.domain.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.token.dto.EsiTokenDto;
import space.uselessidea.uibackend.domain.token.dto.TokenDataDto;
import space.uselessidea.uibackend.domain.token.port.secondary.EveAuthSecondaryPort;
import space.uselessidea.uibackend.domain.token.port.secondary.TokenSecondaryPort;
import space.uselessidea.uibackend.properties.EveProperties;

class TokenServiceTest {

  @Test
  void shouldReturnEmptyWhenRefreshTokenCallFails() {
    EveAuthSecondaryPort eveAuthSecondaryPort = mock(EveAuthSecondaryPort.class);
    TokenSecondaryPort tokenSecondaryPort = mock(TokenSecondaryPort.class);
    EveProperties eveProperties = mock(EveProperties.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    Queue characterUpdateQueue = mock(Queue.class);

    TokenService tokenService =
        new TokenService(
            eveAuthSecondaryPort,
            tokenSecondaryPort,
            eveProperties,
            rabbitTemplate,
            characterUpdateQueue);

    HttpClientErrorException httpException =
        HttpClientErrorException.create(
            HttpStatus.BAD_REQUEST,
            "bad request",
            HttpHeaders.EMPTY,
            new byte[0],
            StandardCharsets.UTF_8);
    when(eveAuthSecondaryPort.refreshToken("refresh-token")).thenThrow(httpException);

    Optional<String> result =
        tokenService.refreshToken(
            EsiTokenDto.builder().id(1L).refreshToken("refresh-token").build());

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnAccessTokenWhenRefreshSucceeds() {
    EveAuthSecondaryPort eveAuthSecondaryPort = mock(EveAuthSecondaryPort.class);
    TokenSecondaryPort tokenSecondaryPort = mock(TokenSecondaryPort.class);
    EveProperties eveProperties = mock(EveProperties.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    Queue characterUpdateQueue = mock(Queue.class);

    TokenService tokenService =
        new TokenService(
            eveAuthSecondaryPort,
            tokenSecondaryPort,
            eveProperties,
            rabbitTemplate,
            characterUpdateQueue);
    TokenService tokenServiceSpy = org.mockito.Mockito.spy(tokenService);

    TokenDataDto refreshed =
        TokenDataDto.builder()
            .accessToken("new-access-token")
            .refreshToken("refresh-token")
            .build();
    when(eveAuthSecondaryPort.refreshToken("refresh-token")).thenReturn(refreshed);
    doReturn(42L).when(tokenServiceSpy).addToken(any(TokenDataDto.class));

    Optional<String> result =
        tokenServiceSpy.refreshToken(
            EsiTokenDto.builder().id(42L).refreshToken("refresh-token").build());

    assertEquals(Optional.of("new-access-token"), result);
  }

  @Test
  void shouldReturnEmptyWhenRefreshedTokenIsInvalid() {
    EveAuthSecondaryPort eveAuthSecondaryPort = mock(EveAuthSecondaryPort.class);
    TokenSecondaryPort tokenSecondaryPort = mock(TokenSecondaryPort.class);
    EveProperties eveProperties = mock(EveProperties.class);
    RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    Queue characterUpdateQueue = mock(Queue.class);

    TokenService tokenService =
        new TokenService(
            eveAuthSecondaryPort,
            tokenSecondaryPort,
            eveProperties,
            rabbitTemplate,
            characterUpdateQueue);
    TokenService tokenServiceSpy = org.mockito.Mockito.spy(tokenService);

    TokenDataDto refreshed =
        TokenDataDto.builder()
            .accessToken("new-access-token")
            .refreshToken("refresh-token")
            .build();
    when(eveAuthSecondaryPort.refreshToken("refresh-token")).thenReturn(refreshed);
    doThrow(new ApplicationException(ErrorCode.ACCESS_TOKEN_IS_INVALID))
        .when(tokenServiceSpy)
        .addToken(any(TokenDataDto.class));

    Optional<String> result =
        tokenServiceSpy.refreshToken(
            EsiTokenDto.builder().id(7L).refreshToken("refresh-token").build());

    assertTrue(result.isEmpty());
  }
}
