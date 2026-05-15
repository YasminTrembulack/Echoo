package com.trycatchus.echoo.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trycatchus.echoo.dtos.payloads.location.LocationPayload;
import com.trycatchus.echoo.dtos.payloads.location.LocationUpdatePayload;
import com.trycatchus.echoo.dtos.responses.LocationResponse;
import com.trycatchus.echoo.exceptions.EntityNotFoundException;
import com.trycatchus.echoo.exceptions.UniqueFieldAlreadyInUseException;
import com.trycatchus.echoo.interfaces.LocationService;
import com.trycatchus.echoo.mappers.LocationMapper;
import com.trycatchus.echoo.models.Location;
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

    private void validateUniqueFields(String postalCode, String complement, String number, UUID locationIdToExclude) {
        Boolean exists = locationRepo.existsConflictingLocation(
            postalCode, complement, number, locationIdToExclude);
        
        if (exists) {
            List<String> uniqueFields = List.of("postalCode", "complement", "number");
            throw new UniqueFieldAlreadyInUseException(Location.class, uniqueFields);
        }
    }

    @Override
    public LocationResponse create(LocationPayload payload) {
        validateUniqueFields(payload.postalCode(), payload.complement(), payload.number(), null);

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
        
        validateUniqueFields(location.getPostalCode(), location.getComplement(), location.getNumber(), location.getId());

        location.setEstablishment(UpdateUtils.valueOrKeep(payload.establishment(), location.getEstablishment()));
        location.setNeighborhood(UpdateUtils.valueOrKeep(payload.neighborhood(), location.getNeighborhood()));
        location.setCountry(UpdateUtils.valueOrKeep(payload.country(), location.getCountry()));
        location.setStreet(UpdateUtils.valueOrKeep(payload.street(), location.getStreet()));
        location.setState(UpdateUtils.valueOrKeep(payload.state(), location.getState()));
        location.setCity(UpdateUtils.valueOrKeep(payload.city(), location.getCity()));

        Location updatedLocation = locationRepo.save(location);

        return locationMapper.toResponse(updatedLocation);
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
            .collect(Collectors.toList());
    }
    
}
