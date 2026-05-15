package com.trycatchus.echoo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.dtos.payloads.location.LocationPayload;
import com.trycatchus.echoo.dtos.responses.LocationResponse;
import com.trycatchus.echoo.models.Location;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LocationMapper extends BaseMapper<LocationPayload, Location, LocationResponse> { }