package pl.tscript3r.fruitstore.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.tscript3r.fruitstore.api.v1.mapper.VendorMapper;
import pl.tscript3r.fruitstore.api.v1.model.VendorDTO;
import pl.tscript3r.fruitstore.domain.Vendor;
import pl.tscript3r.fruitstore.repositories.VendorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    public static final String NAME = "NAME";
    public static final String URL = "URL";
    public static final Long ID = 1L;
    private static final String NAME_PATCHED = "NAME_PATCHED";
    private static final String URL_PATCHED = "URL_PATCHED";
    private VendorService vendorService;

    @Mock
    private VendorRepository vendorRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendors() {
        List<Vendor> vendorList = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendorList);

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(3, vendorDTOS.size());
    }

    @Test
    public void getVendorByName() {
        Vendor vendor = Vendor.builder().name(NAME).vendorUrl(URL).id(ID).build();
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        VendorDTO returnedVendorDTO = vendorService.getVendorById(ID);
        assertEquals(returnedVendorDTO.getName(), vendor.getName());
        assertEquals(returnedVendorDTO.getVendorUrl(), vendor.getVendorUrl());
    }

    @Test
    public void testCreateNewVendor() {
        VendorMapper vendorMapper = VendorMapper.INSTANCE;
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).vendorUrl(URL).build();
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(ID);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO returnedVendorDTO = vendorService.createNewVendor(vendorDTO);
        assertEquals(NAME, returnedVendorDTO.getName());
        assertEquals(URL, returnedVendorDTO.getVendorUrl());
    }

    @Test
    public void testUpdateVendor() {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME_PATCHED).build();
        Vendor vendor = Vendor.builder().id(ID).name(NAME_PATCHED).vendorUrl(URL).build();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO savedDTO = vendorService.updateVendor(ID, vendorDTO);

        assertEquals(vendorDTO.getName(), savedDTO.getName());
    }

    @Test
    public void testPatchVendor() {
        Vendor vendor = Vendor.builder().id(ID).name(NAME).vendorUrl(URL).build();
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME_PATCHED).vendorUrl(URL_PATCHED).build();
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        VendorDTO returnedVendorDTO = vendorService.patchVendor(ID, vendorDTO);
        assertEquals(vendorDTO.getName(), returnedVendorDTO.getName());
        assertEquals(vendorDTO.getVendorUrl(), returnedVendorDTO.getVendorUrl());
    }

    @Test
    public void testDeleteVendor() {
        Long id = 1L;
        vendorRepository.deleteById(id);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}