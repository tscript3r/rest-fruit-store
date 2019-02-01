package pl.tscript3r.fruitstore.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.tscript3r.fruitstore.api.v1.model.CustomerDTO;
import pl.tscript3r.fruitstore.services.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest {
    private static final String NAME = "Jim";
    private static final String JOHN = "John";
    private static final String URL = "URL";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListCustomers() throws Exception {
        CustomerDTO customerDTO1 = CustomerDTO.builder().firstname(NAME).build();
        CustomerDTO customerDTO2 = CustomerDTO.builder().firstname(JOHN).build();
        List<CustomerDTO> customerDTOS = Arrays.asList(customerDTO1, customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetUserById() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().firstname(NAME).build();

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));
    }

    @Test
    public void testCreateNewUser() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().firstname(NAME).lastname(JOHN).customerUrl(URL).build();

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(JOHN)))
                .andExpect(jsonPath("$.customer_url", equalTo(URL)));
    }

    @Test
    public void testUpdateUser() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder().firstname(NAME).lastname(JOHN).customerUrl(URL).build();

        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(JOHN)))
                .andExpect(jsonPath("$.customer_url", equalTo(URL)));
    }

}
