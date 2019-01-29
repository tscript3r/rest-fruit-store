package pl.tscript3r.fruitstore.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.tscript3r.fruitstore.api.v1.model.CategoryDTO;
import pl.tscript3r.fruitstore.domain.Category;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDTO categoryToCategoryDTO(Category category);
}
