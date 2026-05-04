package com.trycatchus.echoo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.model.Location;
import com.trycatchus.echoo.dto.payload.location.LocationPayload;
import com.trycatchus.echoo.dto.responses.LocationResponse;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LocationMapper extends BaseMapper<LocationPayload, Location, LocationResponse> { }