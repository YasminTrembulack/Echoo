package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dto.payload.event.EventPayload;
import com.trycatchus.echoo.dto.payload.event.EventUpdatePayload;
import com.trycatchus.echoo.dto.responses.EventResponse;

public interface EventService extends 
    BaseService<EventPayload, EventUpdatePayload, EventResponse, String> 
{ }