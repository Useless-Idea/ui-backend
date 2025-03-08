package space.uselessidea.uibackend.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  ACCESS_DENIED("error.access.denied", HttpStatus.UNAUTHORIZED),
  ACCESS_TOKEN_IS_INVALID("error.access.token.is.invalid", HttpStatus.FORBIDDEN),
  CHARACTER_NOT_EXIST("error.character.not.exist", HttpStatus.BAD_REQUEST);

  private final String code;
  private final HttpStatus httpStatus;

  ErrorCode(String code) {
    this(code, HttpStatus.BAD_REQUEST);
  }
}
