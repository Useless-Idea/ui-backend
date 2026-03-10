package space.uselessidea.uibackend.domain.fit.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Builder
@Data
public class SearchFitDto {

  private String fitName;
  private List<String> pilots;
  private List<String> ships;
  private int page;
  private int size;

  public Pageable toPageable() {
    return PageRequest.of(page, size);
  }
}
