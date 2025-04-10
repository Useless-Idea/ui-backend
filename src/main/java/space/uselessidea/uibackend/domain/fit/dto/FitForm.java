package space.uselessidea.uibackend.domain.fit.dto;

import lombok.Data;

@Data
public class FitForm {


  private String fit;
  private String description;


  public static FitForm fromEft(String eft) {
    FitForm fitForm = new FitForm();
    fitForm.setFit(eft);
    return fitForm;

  }

}
