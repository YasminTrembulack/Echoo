package com.trycatchus.echoo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.trycatchus.echoo.dto.payload.event.EventPayload;
import com.trycatchus.echoo.dto.payload.event.EventUpdatePayload;
import com.trycatchus.echoo.dto.responses.EventResponse;
import com.trycatchus.echoo.dto.responses.auth.UserDecodedResponse;
import com.trycatchus.echoo.enums.UserRole;
import com.trycatchus.echoo.exception.EntityNotFoundException;
import com.trycatchus.echoo.exception.InvalidDateRangeException;
import com.trycatchus.echoo.exception.UnauthorizedException;
import com.trycatchus.echoo.exception.UniqueFieldAlreadyInUseException;
import com.trycatchus.echoo.interfaces.EventService;
import com.trycatchus.echoo.mapper.EventMapper;
import com.trycatchus.echoo.model.Event;
import com.trycatchus.echoo.model.Location;
import com.trycatchus.echoo.model.User;
import com.trycatchus.echoo.repository.EventRepository;
import com.trycatchus.echoo.repository.LocationRepository;
import com.trycatchus.echoo.repository.UserRepository;
import com.trycatchus.echoo.utils.SecurityUtils;
import com.trycatchus.echoo.utils.UpdateUtils;


public class DefaultEventService implements EventService {

    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    public DefaultEventService(
        LocationRepository locationRepository, 
        EventRepository eventRepository, 
        UserRepository userRepository, 
        EventMapper eventMapper) {
        this.locationRepository = locationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventMapper = eventMapper;
    }

    private void validateEventConflicts(LocalDateTime startDate, LocalDateTime endDate, UUID locationId, UUID eventIdToExclude) {
        Boolean exists = eventRepository.existsConflictingEvent(
            startDate, endDate, locationId, eventIdToExclude);
        
        if (exists) {
            List<String> uniqueFields = List.of("startDate", "endDate", "locationId");
            throw new UniqueFieldAlreadyInUseException("Event", uniqueFields);
        }
    }

    private void validateEventOwnership(Event event) {
        UserDecodedResponse user = SecurityUtils.getCurrentUser();

        UserRole userRole = UserRole.fromString(user.role());

        if (!event.getOrganizer().getId().toString().equals(user.userId()) && userRole != UserRole.ADMIN) {
            throw new UnauthorizedException("You are not the organizer of this event.");
        }
    }

    private void validateDateOrder(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate))
            throw new InvalidDateRangeException();
    }

    private User getOrganizerById(String organizerId) {
        return userRepository.findById(UUID.fromString(organizerId))
            .orElseThrow(() -> new EntityNotFoundException(User.class));
    }

    private Location getLocationById(String locationId) {
        return locationRepository.findById(UUID.fromString(locationId))
            .orElseThrow(() -> new EntityNotFoundException(Location.class));
    }
        
    @Override
    public EventResponse create(EventPayload payload) {
        validateDateOrder(payload.startDate(), payload.endDate());

        validateEventConflicts(
            payload.startDate(), 
            payload.endDate(), 
            UUID.fromString(payload.locationId()),
            null
        );

        Event event = eventMapper.toEntity(payload);
    
        UserDecodedResponse user = SecurityUtils.getCurrentUser();

        User organizer = getOrganizerById(user.userId());

        event.setOrganizer(organizer);

        if (payload.locationId() != null) {
            Location location = getLocationById(payload.locationId());

            event.setLocation(location);
        }

        Event savedEvent = eventRepository.save(event);

        return eventMapper.toResponse(savedEvent);
    }

    @Override
    public EventResponse update(String id, EventUpdatePayload payload) {
        Event event = eventRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Event.class));

        validateEventOwnership(event);
        
        event.setStartDate(UpdateUtils.valueOrKeep(payload.startDate(), event.getStartDate()));
        event.setEndDate(UpdateUtils.valueOrKeep(payload.endDate(), event.getEndDate()));

        validateDateOrder(event.getStartDate(), event.getEndDate());

        if (payload.locationId() != null){
            Location location = getLocationById(payload.locationId());

            event.setLocation(location);
        }

        validateEventConflicts(event.getStartDate(), event.getEndDate(), event.getLocation().getId(), event.getId());

        event.setName(UpdateUtils.valueOrKeep(payload.name(), event.getName()));
        event.setDescription(UpdateUtils.valueOrKeep(payload.description(), event.getDescription()));
        event.setCapacity(UpdateUtils.valueOrKeep(payload.capacity(), event.getCapacity()));
        event.setEventStatus(UpdateUtils.valueOrKeep(payload.eventStatus(), event.getEventStatus()));

        if (payload.organizerId() != null) {
            User organizer = getOrganizerById(payload.organizerId());

            if (organizer.getUserRole() != UserRole.ORGANIZER) {
                throw new UnauthorizedException("The specified user is not an organizer.");
            }

            event.setOrganizer(organizer);
        }

        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toResponse(updatedEvent);

    }

    @Override
    public void delete(String id) {
        Event event = eventRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Event.class));
        
        validateEventOwnership(event);
        
        eventRepository.delete(event);
    }

    @Override
    public EventResponse findById(String id) {
        Event event = eventRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Event.class));
        
        return eventMapper.toResponse(event);
    }

    @Override
    public List<EventResponse> findAll() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
            .map(eventMapper::toResponse)
            .collect(Collectors.toList());
    }
    
}
