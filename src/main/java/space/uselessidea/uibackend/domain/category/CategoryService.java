package space.uselessidea.uibackend.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.category.dto.CategoryDto;
import space.uselessidea.uibackend.domain.category.port.CategoryPrimaryPort;
import space.uselessidea.uibackend.domain.eve.api.secondary.EveApiPort;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryPrimaryPort {

  private final EveApiPort eveApiPort;

  @Override
  public CategoryDto getCategoryDataById(Long id) {
    return eveApiPort.getCategoryDataById(id);
  }
}
