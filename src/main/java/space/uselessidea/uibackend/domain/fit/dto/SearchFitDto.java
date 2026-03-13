package space.uselessidea.uibackend.domain.fit.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Builder
@Data
public class SearchFitDto {

  private String fitName;
  @Builder.Default private List<String> pilots = new ArrayList<>();
  @Builder.Default private List<String> ships = new ArrayList<>();
  @Builder.Default private List<String> doctrines = new ArrayList<>();
  private int page;
  private int size;

  public SearchFitDto normalizeLists() {
    if (pilots == null) {
      pilots = new ArrayList<>();
    }
    if (ships == null) {
      ships = new ArrayList<>();
    }
    if (doctrines == null) {
      doctrines = new ArrayList<>();
    }
    return this;
  }

  public Pageable toPageable() {
    return PageRequest.of(page, size);
  }
}
