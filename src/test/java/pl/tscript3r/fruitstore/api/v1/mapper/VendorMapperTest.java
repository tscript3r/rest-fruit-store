package pl.tscript3r.fruitstore.api.v1.mapper;

import org.junit.Test;
import pl.tscript3r.fruitstore.api.v1.model.VendorDTO;
import pl.tscript3r.fruitstore.domain.Vendor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class VendorMapperTest {

    public static final long ID = 1L;
    public static final String NAME = "NAME";
    public static final String URL = "URL";
    private VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void testVendorToVendorDTO() {
        Vendor vendor = Vendor.builder().id(ID).name(NAME).vendorUrl(URL).build();
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(vendor.getName(), vendorDTO.getName());
        assertEquals(vendor.getVendorUrl(), vendorDTO.getVendorUrl());
    }

    @Test
    public void testVendorDTOToVendor() {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).vendorUrl(URL).build();
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        assertNull(vendor.getId());
        assertEquals(vendorDTO.getName(), vendor.getName());
        assertEquals(vendorDTO.getVendorUrl(), vendor.getVendorUrl());
    }

}