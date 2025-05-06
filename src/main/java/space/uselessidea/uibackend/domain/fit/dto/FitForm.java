package space.uselessidea.uibackend.domain.fit.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonNaming(
    PropertyNamingStrategies.SnakeCaseStrategy.class
)

public class FitForm {

  @JsonSerialize(using = NewLineSerializer.class)
  private String fit;
  @JsonSerialize(using = NewLineSerializer.class)
  private String description;


  public static FitForm fromEft(String eft) {
    FitForm fitForm = new FitForm();
    fitForm.setFit(eft);
    return fitForm;

  }

}
