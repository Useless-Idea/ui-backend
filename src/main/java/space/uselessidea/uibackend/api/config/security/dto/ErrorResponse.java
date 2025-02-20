package space.uselessidea.uibackend.api.config.security.dto;

import java.util.Date;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)
public class ErrorResponse {

  private Date timestamp;

  private String path;
  @SerializedName("error_code")
  private String errorCode;
  @SerializedName("error_message")
  private String errorMessage;

  public static ErrorResponse of(String path, String errorCode, String errorMessage) {
    return ErrorResponse.builder()
        .timestamp(new Date())
        .path(path)
        .errorCode(errorCode)
        .errorMessage(errorMessage)
        .build();
  }

}
