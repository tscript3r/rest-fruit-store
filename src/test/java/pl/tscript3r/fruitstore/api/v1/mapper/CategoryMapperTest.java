package pl.tscript3r.fruitstore.api.v1.mapper;

import org.junit.Test;
import pl.tscript3r.fruitstore.api.v1.model.CategoryDTO;
import pl.tscript3r.fruitstore.domain.Category;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    private static final String NAME = "xyf";
    private static final long ID = 1L;
    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }

}