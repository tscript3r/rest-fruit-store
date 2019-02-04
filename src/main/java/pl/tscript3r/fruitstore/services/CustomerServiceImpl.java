package pl.tscript3r.fruitstore.services;

import org.springframework.stereotype.Service;
import pl.tscript3r.fruitstore.api.v1.mapper.CustomerMapper;
import pl.tscript3r.fruitstore.api.v1.model.CustomerDTO;
import pl.tscript3r.fruitstore.controllers.v1.CustomerController;
import pl.tscript3r.fruitstore.domain.Customer;
import pl.tscript3r.fruitstore.exceptions.ResourceNotFoundException;
import pl.tscript3r.fruitstore.repositories.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl(getCustomerUrl(id));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    private CustomerDTO saveAndReturnCustomerDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        customerDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return customerDTO;
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnCustomerDTO(customerMapper.customerDTOToCustomer(customerDTO));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnCustomerDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstname() != null)
                customer.setFirstname(customerDTO.getFirstname());
            if(customerDTO.getLastname() != null)
                customer.setLastname(customerDTO.getLastname());

            CustomerDTO returnedCustomerDTO = customerMapper.customerToCustomerDTO(customer);

            returnedCustomerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
            return returnedCustomerDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + id;
    }
}
