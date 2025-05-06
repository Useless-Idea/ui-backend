package space.uselessidea.uibackend.domain.category.port;

import space.uselessidea.uibackend.domain.category.dto.CategoryDto;

public interface CategoryPrimaryPort {

  CategoryDto getCategoryDataById(Long id);

}
