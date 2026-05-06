package com.trycatchus.echoo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import com.trycatchus.echoo.dto.responses.LocationResponse;
import com.trycatchus.echoo.dto.system.ViaCepResponse;
import com.trycatchus.echoo.exception.EntityNotFoundException;
import com.trycatchus.echoo.interfaces.AddressLookupService;
import com.trycatchus.echoo.mapper.LocationMapper;
import com.trycatchus.echoo.model.Location;

@Service
public class ViaCepService implements AddressLookupService{

    private final RestTemplate restTemplate;
    private final LocationMapper locationMapper;

    @Value("${viacep.base-url}")
    private String baseUrl;

    public ViaCepService(RestTemplateBuilder builder, LocationMapper locationMapper) {
        this.restTemplate = builder.build();
        this.locationMapper = locationMapper;
    }

    public LocationResponse lookup(String postalCode) {
        var url = String.format("%s/%s/json/", baseUrl, postalCode.replaceAll("\\D", ""));
        
        ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);

        if (response == null || Boolean.TRUE.equals(response.erro())) {
            throw new EntityNotFoundException("Postal code not found.");
        }

        Location location = Location.builder()
            .postalCode(response.cep())
            .street(response.logradouro())
            .complement(response.complemento())
            .neighborhood(response.bairro())
            .city(response.localidade())
            .state(response.uf())
            .country("Brazil")
            .build();

        return locationMapper.toResponse(location);
    }
}