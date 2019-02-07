package pl.tscript3r.fruitstore.services;

import org.springframework.stereotype.Service;
import pl.tscript3r.fruitstore.api.v1.mapper.VendorMapper;
import pl.tscript3r.fruitstore.api.v1.model.VendorDTO;
import pl.tscript3r.fruitstore.domain.Vendor;
import pl.tscript3r.fruitstore.exceptions.ResourceNotFoundException;
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
                .map(vendorMapper::vendorToVendorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return vendorMapper.vendorToVendorDTO(
                vendorRepository.save(vendorMapper.vendorDTOToVendor(vendorDTO)));
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        Vendor returnedVendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToVendorDTO(returnedVendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {

            if (vendorDTO.getName() != null)
                vendor.setName(vendorDTO.getName());

            if (vendorDTO.getVendorUrl() != null)
                vendor.setVendorUrl(vendorDTO.getVendorUrl());

            return vendorMapper.vendorToVendorDTO(vendor);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendor(Long id) {
        if (vendorRepository.existsById(id))
            vendorRepository.deleteById(id);
        else
            throw new ResourceNotFoundException();
    }
}
