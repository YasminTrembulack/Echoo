package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dtos.responses.LocationResponse;

public interface AddressLookupService {
    LocationResponse lookup(String postalCode);
}