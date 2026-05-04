package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dto.payload.location.LocationPayload;
import com.trycatchus.echoo.dto.payload.location.LocationUpdatePayload;
import com.trycatchus.echoo.dto.responses.LocationResponse;

public interface LocationService extends 
    BaseService<LocationPayload, LocationUpdatePayload, LocationResponse, String> 
{ }