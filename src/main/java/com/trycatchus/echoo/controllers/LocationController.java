package com.trycatchus.echoo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trycatchus.echoo.dtos.payloads.location.LocationPayload;
import com.trycatchus.echoo.dtos.payloads.location.LocationUpdatePayload;
import com.trycatchus.echoo.dtos.responses.DataResponse;
import com.trycatchus.echoo.dtos.responses.LocationResponse;
import com.trycatchus.echoo.interfaces.AddressLookupService;
import com.trycatchus.echoo.interfaces.LocationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController()
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final AddressLookupService addressLookupService;

    public LocationController(LocationService locationService, AddressLookupService addressLookupService) {
        this.addressLookupService = addressLookupService;
        this.locationService = locationService;
    }

    @PostMapping
    public DataResponse<LocationResponse> createLocation(@RequestBody @Valid LocationPayload payload) {
        LocationResponse response = locationService.create(payload);
        return new DataResponse<LocationResponse>("Location created successfully", response);
    }

    @PatchMapping("/{id}")
    public DataResponse<LocationResponse> updateLocation(
        @PathVariable String id,
        @RequestBody @Valid LocationUpdatePayload payload
    ) {
        LocationResponse response = locationService.update(id, payload);
        return new DataResponse<LocationResponse>("Location updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public DataResponse<LocationResponse> getLocation(@PathVariable String id) {
        LocationResponse response = locationService.findById(id);
        return new DataResponse<LocationResponse>("Location retrieved ", response);
    }
    
    @GetMapping
    public DataResponse<List<LocationResponse>> getAllLocations() {
        List<LocationResponse> response = locationService.findAll();
        return new DataResponse<List<LocationResponse>>("Locations retrieved successfully", response);
    }

    @GetMapping("/lookup/{postalCode}")
    public DataResponse<LocationResponse> getLocationByPostalCode(@PathVariable String postalCode){
        LocationResponse response = addressLookupService.lookup(postalCode);
        return new DataResponse<LocationResponse>("Location found successfully", response);
    }

}