package pl.tscript3r.fruitstore.api.v1.mapper;

import org.junit.Test;
import pl.tscript3r.fruitstore.api.v1.model.CustomerDTO;
import pl.tscript3r.fruitstore.domain.Customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomerMapperTest {

    public static final String LASTNAME = "Lastname";
    public static final String FIRSTNAME = "Firstname";
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        Customer customer = Customer.builder().id(1L).lastname(LASTNAME).firstname(FIRSTNAME).build();
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(customer.getLastname(), customerDTO.getLastname());
        assertEquals(customerDTO.getFirstname(), customerDTO.getFirstname());
    }

    @Test
    public void customerDTOToCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().firstname(FIRSTNAME).lastname(LASTNAME).build();
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        assertEquals(customerDTO.getFirstname(), customer.getFirstname());
        assertEquals(customerDTO.getLastname(), customer.getLastname());
        assertNull(customer.getId());
    }

}