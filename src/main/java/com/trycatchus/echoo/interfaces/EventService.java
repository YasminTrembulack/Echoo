package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dtos.payloads.event.EventPayload;
import com.trycatchus.echoo.dtos.payloads.event.EventUpdatePayload;
import com.trycatchus.echoo.dtos.responses.EventResponse;

public interface EventService extends 
    BaseService<EventPayload, EventUpdatePayload, EventResponse, String> 
{ }