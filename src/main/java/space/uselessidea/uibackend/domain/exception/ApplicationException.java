package space.uselessidea.uibackend.domain.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 6863759437811923824L;

  private final ErrorCode errorCode;

  private final transient Object[] args;

  public ApplicationException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
    this.args = null;
  }

  public ApplicationException(ErrorCode errorCode, Object... args) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
    this.args = args;
  }

  public ApplicationException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getCode(), cause);
    this.errorCode = errorCode;
    this.args = null;
  }

  public ApplicationException(ErrorCode errorCode, Object[] args, Throwable cause) {
    super(errorCode.getCode(), cause);
    this.errorCode = errorCode;
    this.args = args;
  }
}
