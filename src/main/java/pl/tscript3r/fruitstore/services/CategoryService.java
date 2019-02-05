package pl.tscript3r.fruitstore.services;

import pl.tscript3r.fruitstore.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
