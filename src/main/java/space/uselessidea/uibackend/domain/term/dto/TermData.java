package space.uselessidea.uibackend.domain.term.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TermData {


  @NotNull
  private String term;

  @NotNull
  private String description;
}
