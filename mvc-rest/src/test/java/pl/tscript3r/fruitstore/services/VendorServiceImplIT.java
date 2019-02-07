package pl.tscript3r.fruitstore.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tscript3r.fruitstore.api.v1.mapper.VendorMapper;
import pl.tscript3r.fruitstore.api.v1.model.VendorDTO;
import pl.tscript3r.fruitstore.bootstrap.DataInitializer;
import pl.tscript3r.fruitstore.domain.Vendor;
import pl.tscript3r.fruitstore.repositories.CategoryRepository;
import pl.tscript3r.fruitstore.repositories.CustomerRepository;
import pl.tscript3r.fruitstore.repositories.VendorRepository;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() {
        DataInitializer dataInitializer = new DataInitializer(categoryRepository, customerRepository, vendorRepository);
        dataInitializer.run();

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    private Long getVendorIdValue() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.get(0).getId();
    }

    @Test
    public void patchVendorUpdateName() {
        String updatedName = "UpdatedName";
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);

        String originalName = originalVendor.getName();
        String originalUrl = originalVendor.getVendorUrl();

        VendorDTO vendorDTO = VendorDTO.builder().name(updatedName).build();

        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.getOne(id);

        assertNotNull(updatedVendor);
        assertEquals(updatedName, updatedVendor.getName());
        assertThat(originalName, not(equalTo(updatedVendor.getName())));
        assertThat(originalUrl, equalTo(updatedVendor.getVendorUrl()));
    }

    @Test
    public void patchVendorUpdateUrl() {
        String updatedUrl = "UpdatedName";
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);

        String originalName = originalVendor.getName();
        String originalUrl = originalVendor.getVendorUrl();

        VendorDTO vendorDTO = VendorDTO.builder().vendorUrl(updatedUrl).build();

        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.getOne(id);

        assertNotNull(updatedVendor);
        assertEquals(updatedUrl, updatedVendor.getVendorUrl());
        assertThat(originalName, equalTo(updatedVendor.getName()));
        assertThat(originalUrl, not(equalTo(updatedVendor.getVendorUrl())));
    }
}
