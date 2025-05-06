package space.uselessidea.uibackend.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import space.uselessidea.uibackend.api.config.security.dto.ErrorResponse;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private static final String ERROR = "Error!";
  private final MessageSource messageSource;

  @ExceptionHandler(
      ApplicationException.class
  )
  public ResponseEntity<ErrorResponse> handleApplicationExceptions(
      ApplicationException exception,
      Locale locale,
      HttpServletRequest request
  ) {
    log.error(ERROR, exception);
    return generateResponseByErrorCode(exception.getErrorCode(), locale, request, exception.getArgs());
  }

  private ResponseEntity<ErrorResponse> generateResponseByErrorCode(
      ErrorCode errorCodeEnum,
      Locale locale,
      HttpServletRequest request,
      Object[] args
  ) {
    String errorMessage = null;
    String errorCode = errorCodeEnum.getCode();
    if (errorCode != null) {
      errorMessage = messageSource.getMessage(errorCode, args, errorCode, locale);
    }

    HttpStatus status = errorCodeEnum.getHttpStatus();
    ErrorResponse responseError = ErrorResponse.of(request.getRequestURI(), errorCode,
        errorMessage);
    return ResponseEntity.status(status).body(responseError);
  }

}
