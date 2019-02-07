package pl.tscript3r.fruitstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tscript3r.fruitstore.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
