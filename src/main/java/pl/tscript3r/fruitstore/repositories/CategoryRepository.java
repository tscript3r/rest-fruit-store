package pl.tscript3r.fruitstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tscript3r.fruitstore.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

}
