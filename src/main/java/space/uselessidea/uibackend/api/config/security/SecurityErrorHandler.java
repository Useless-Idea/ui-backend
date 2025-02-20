package space.uselessidea.uibackend.api.config.security;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import space.uselessidea.uibackend.api.config.security.dto.ErrorResponse;
import space.uselessidea.uibackend.domain.exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class SecurityErrorHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

  private final MessageSource messageSource;
  private final Gson gson;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    ErrorCode errorCode = ErrorCode.ACCESS_TOKEN_IS_INVALID;
    generateErrorResponse(errorCode, request, response);

  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
    generateErrorResponse(errorCode, request, response);

  }

  private void generateErrorResponse(ErrorCode errorCode, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setStatus(errorCode.getHttpStatus().value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    ErrorResponse errorResponse = ErrorResponse.of(
        request.getRequestURI(),
        errorCode.getCode(),
        messageSource.getMessage(errorCode.getCode(), null,
            request.getLocale())
    );
    response.getWriter().write(gson.toJson(errorResponse));
  }


}
