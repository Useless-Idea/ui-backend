package space.uselessidea.uibackend.domain.exception;

import java.io.Serial;
import java.util.Arrays;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ApplicationException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 6863759437811923824L;

  private final ErrorCode errorCode;

  private final transient Object[] args;

  public ApplicationException(ErrorCode errorCode) {
    this(errorCode, null, null);
  }

  public ApplicationException(ErrorCode errorCode, Object... args) {
    this(errorCode, args, null);
  }

  public ApplicationException(ErrorCode errorCode, Throwable cause) {
    this(errorCode, null, cause);
  }

  public ApplicationException(ErrorCode errorCode, Object[] args, Throwable cause) {
    super(errorCode.getCode(), cause);
    this.errorCode = errorCode;
    this.args = args;
    log.error(String.format("""
        ErrorCode: %s
        Args: %s
        """, errorCode.getCode(), Arrays.toString(args)));
  }
}
