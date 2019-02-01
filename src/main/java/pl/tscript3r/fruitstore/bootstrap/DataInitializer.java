package pl.tscript3r.fruitstore.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.tscript3r.fruitstore.domain.Category;
import pl.tscript3r.fruitstore.domain.Customer;
import pl.tscript3r.fruitstore.repositories.CategoryRepository;
import pl.tscript3r.fruitstore.repositories.CustomerRepository;

import java.util.Arrays;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public DataInitializer(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {

        // Adding categories
        categoryRepository.saveAll(
                Arrays.asList(Category.builder().name("Fruits").build(),
                    Category.builder().name("Dried").build(),
                    Category.builder().name("Fresh").build(),
                    Category.builder().name("Exotic").build(),
                    Category.builder().name("Nuts").build())
        );

        // Adding users
        customerRepository.saveAll(
                Arrays.asList(Customer.builder().firstname("Alex").lastname("Kowalski").build(),
                        Customer.builder().firstname("Katarzyna").lastname("Root").build(),
                        Customer.builder().firstname("Bartosz").lastname("Endermauer").build(),
                        Customer.builder().firstname("Damian").lastname("Sucharewicz").build())
        );

        log.debug("Bootstrap data initialized");
    }

}
