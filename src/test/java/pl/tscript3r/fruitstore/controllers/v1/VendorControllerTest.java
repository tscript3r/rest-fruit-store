package pl.tscript3r.fruitstore.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.tscript3r.fruitstore.api.v1.model.VendorDTO;
import pl.tscript3r.fruitstore.services.VendorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    private static final String NAME = "Jim";
    private static final String NAME2 = "John";
    private static final String URL = "https://test.com/";
    public static final Long ID = 1L;

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllVendors() throws Exception {
        VendorDTO vendorDTO1 = VendorDTO.builder().name(NAME).build();
        VendorDTO vendorDTO2 = VendorDTO.builder().name(NAME2).build();
        List<VendorDTO> vendorDTOS = Arrays.asList(vendorDTO1, vendorDTO2);

        when(vendorService.getAllVendors()).thenReturn(vendorDTOS);

        mockMvc.perform(get(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetVendorById() throws Exception {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).vendorUrl(URL).build();
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(get(VendorController.BASE_URL + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(URL)));
    }

    @Test
    public void testCreateNewVendor() throws Exception {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).vendorUrl(URL).build();
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(vendorDTO);
        mockMvc.perform(post(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(URL)));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).vendorUrl(URL).build();
        when(vendorService.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO);

        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(URL)));
    }

    @Test
    public void testPatchVendor() throws Exception {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME2).build();
        VendorDTO returnVendorDTO =
                VendorDTO.builder().name(vendorDTO.getName()).vendorUrl(URL).build();

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnVendorDTO);

        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME2)))
                .andExpect(jsonPath("$.vendor_url", equalTo(URL)));
    }

    @Test
    public void testDeleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}