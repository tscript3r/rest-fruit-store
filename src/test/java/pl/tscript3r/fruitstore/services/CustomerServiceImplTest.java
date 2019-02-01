package pl.tscript3r.fruitstore.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.tscript3r.fruitstore.api.v1.mapper.CustomerMapper;
import pl.tscript3r.fruitstore.api.v1.model.CustomerDTO;
import pl.tscript3r.fruitstore.domain.Customer;
import pl.tscript3r.fruitstore.repositories.CustomerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    private static final Long ID = 1L;
    public static final String FIRSTNAME = "A";
    public static final String LASTNAME = "B";
    public static final String CUSTOMER_URL = "/api/v1/customer/";
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {
        Customer customer = Customer.builder()
                .id(ID)
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .build();
        when(customerRepository.findById(anyLong()))
                .thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }

    @Test
    public void addNewCustomer() {
        CustomerMapper customerMapper = CustomerMapper.INSTANCE;
        CustomerDTO customerDTO = CustomerDTO.builder().firstname(FIRSTNAME).lastname(LASTNAME).build();
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(ID);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO returnedCustomerDTO = customerService.createNewCustomer(customerDTO);

        assertEquals(FIRSTNAME, returnedCustomerDTO.getFirstname());
        assertEquals(LASTNAME, returnedCustomerDTO.getLastname());
        assertEquals(CUSTOMER_URL + ID, returnedCustomerDTO.getCustomerUrl());
    }

    @Test
    public void updateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1l);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDTO = customerService.updateCustomer(1L, customerDTO);

        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals("/api/v1/customer/1", savedDTO.getCustomerUrl());
    }


}