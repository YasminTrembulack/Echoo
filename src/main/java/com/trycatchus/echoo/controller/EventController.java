package com.trycatchus.echoo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trycatchus.echoo.dto.payload.event.EventPayload;
import com.trycatchus.echoo.dto.payload.event.EventUpdatePayload;
import com.trycatchus.echoo.dto.responses.DataResponse;
import com.trycatchus.echoo.dto.responses.EventResponse;
import com.trycatchus.echoo.interfaces.EventService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController()
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public DataResponse<EventResponse> createEvent(@RequestBody @Valid EventPayload payload) {
        EventResponse response = eventService.create(payload);
        return new DataResponse<EventResponse>("Event created successfully", response);
    }

    @PatchMapping("/{id}")
    public DataResponse<EventResponse> updateEvent(
        @PathVariable String id,
        @RequestBody @Valid EventUpdatePayload payload
    ) {
        EventResponse response = eventService.update(id, payload);
        return new DataResponse<EventResponse>("Event updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public DataResponse<EventResponse> getEvent(@PathVariable String id) {
        EventResponse response = eventService.findById(id);
        return new DataResponse<EventResponse>("Event retrieved successfully", response);
    }
    
    @GetMapping
    public DataResponse<java.util.List<EventResponse>> getAllEvents() {
        java.util.List<EventResponse> response = eventService.findAll();
        return new DataResponse<java.util.List<EventResponse>>("Events retrieved successfully", response);
    }

}