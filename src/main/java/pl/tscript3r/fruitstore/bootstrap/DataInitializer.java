package pl.tscript3r.fruitstore.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.tscript3r.fruitstore.domain.Category;
import pl.tscript3r.fruitstore.repositories.CategoryRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        List<Category> categories = Arrays.asList(Category.builder().name("Fruits").build(),
                Category.builder().name("Dried").build(),
                Category.builder().name("Fresh").build(),
                Category.builder().name("Exotic").build(),
                Category.builder().name("Nuts").build());

        categories.forEach(categoryRepository::save);

        log.debug("Bootstrap data initialized");
    }

}
