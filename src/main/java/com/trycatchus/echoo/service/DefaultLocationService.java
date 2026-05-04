package com.trycatchus.echoo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trycatchus.echoo.dto.payload.location.LocationPayload;
import com.trycatchus.echoo.dto.payload.location.LocationUpdatePayload;
import com.trycatchus.echoo.dto.responses.LocationResponse;
import com.trycatchus.echoo.exception.EntityNotFoundException;
import com.trycatchus.echoo.exception.UniqueFieldAlreadyInUseException;
import com.trycatchus.echoo.interfaces.LocationService;
import com.trycatchus.echoo.mapper.LocationMapper;
import com.trycatchus.echoo.model.Location;
import com.trycatchus.echoo.repository.LocationRepository;
import com.trycatchus.echoo.utils.UpdateUtils;

@Service
public class DefaultLocationService implements LocationService {

    private final LocationRepository locationRepo;
    private final LocationMapper locationMapper;

    public DefaultLocationService(LocationRepository locationRepo, LocationMapper locationMapper) {
        this.locationRepo = locationRepo;
        this.locationMapper = locationMapper;
    }

    private void validateUniqueFields(String postalCode, String complement, String number) {
        Optional<Location> existingLocation =
            locationRepo.findConflictingLocations(postalCode, complement, number);
        
        
        if (existingLocation.isPresent()) {
            List<String> uniqueFields = List.of("postalCode", "complement", "number");
            throw new UniqueFieldAlreadyInUseException("Location", uniqueFields);
        }
    }

    @Override
    public LocationResponse create(LocationPayload payload) {
        validateUniqueFields(payload.postalCode(), payload.complement(), payload.number());

        Location location = locationMapper.toEntity(payload);

        Location savedLocation = locationRepo.save(location);

        return locationMapper.toResponse(savedLocation);
    }

    @Override
    public LocationResponse update(String id, LocationUpdatePayload payload) {
        Location location = locationRepo.findById(java.util.UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Location.class));
        
        location.setPostalCode(UpdateUtils.valueOrKeep(payload.postalCode(), location.getPostalCode()));
        location.setComplement(UpdateUtils.valueOrKeep(payload.complement(), location.getComplement()));
        location.setNumber(UpdateUtils.valueOrKeep(payload.number(), location.getNumber()));
        
        validateUniqueFields(location.getPostalCode(), location.getComplement(), location.getNumber());

        location.setEstablishment(UpdateUtils.valueOrKeep(payload.establishment(), location.getEstablishment()));
        location.setNeighborhood(UpdateUtils.valueOrKeep(payload.neighborhood(), location.getNeighborhood()));
        location.setCountry(UpdateUtils.valueOrKeep(payload.country(), location.getCountry()));
        location.setStreet(UpdateUtils.valueOrKeep(payload.street(), location.getStreet()));
        location.setState(UpdateUtils.valueOrKeep(payload.state(), location.getState()));
        location.setCity(UpdateUtils.valueOrKeep(payload.city(), location.getCity()));

        return locationMapper.toResponse(location);
    }

    @Override
    public void delete(String id) {
        Location location = locationRepo.findById(java.util.UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Location.class));

        locationRepo.delete(location);
    }

    @Override
    public LocationResponse findById(String id) {
        Location location = locationRepo.findById(java.util.UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Location.class));

        return locationMapper.toResponse(location);
    }

    @Override
    public List<LocationResponse> findAll() {
        List<Location> locations = locationRepo.findAll();
    
        return locations.stream()
            .map(locationMapper::toResponse)
            .collect(java.util.stream.Collectors.toList());
    }
    
}
