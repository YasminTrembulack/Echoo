package com.trycatchus.echoo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trycatchus.echoo.dto.payload.location.LocationPayload;
import com.trycatchus.echoo.dto.payload.location.LocationUpdatePayload;
import com.trycatchus.echoo.dto.responses.DataResponse;
import com.trycatchus.echoo.dto.responses.LocationResponse;
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

    public LocationController(LocationService locationService) {
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
        return new DataResponse<LocationResponse>("Location retrieved successfully", response);
    }
    
    @GetMapping
    public DataResponse<java.util.List<LocationResponse>> getAllLocations() {
        java.util.List<LocationResponse> response = locationService.findAll();
        return new DataResponse<java.util.List<LocationResponse>>("Locations retrieved successfully", response);
    }

}