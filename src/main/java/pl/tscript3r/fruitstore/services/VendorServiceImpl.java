package pl.tscript3r.fruitstore.services;

import org.springframework.stereotype.Service;
import pl.tscript3r.fruitstore.api.v1.mapper.VendorMapper;
import pl.tscript3r.fruitstore.api.v1.model.VendorDTO;
import pl.tscript3r.fruitstore.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor ->
                    vendorMapper.vendorToVendorDTO(vendor)
                ).collect(Collectors.toList());
    }

}
