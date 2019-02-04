package pl.tscript3r.fruitstore.services;

import pl.tscript3r.fruitstore.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();
}
