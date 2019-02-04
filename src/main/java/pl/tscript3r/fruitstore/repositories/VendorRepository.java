package pl.tscript3r.fruitstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tscript3r.fruitstore.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
