package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dto.responses.LocationResponse;

public interface AddressLookupService {
    LocationResponse lookup(String postalCode);
}