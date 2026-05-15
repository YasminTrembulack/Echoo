package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dtos.payloads.location.LocationPayload;
import com.trycatchus.echoo.dtos.payloads.location.LocationUpdatePayload;
import com.trycatchus.echoo.dtos.responses.LocationResponse;

public interface LocationService extends 
    BaseService<LocationPayload, LocationUpdatePayload, LocationResponse, String> 
{ }