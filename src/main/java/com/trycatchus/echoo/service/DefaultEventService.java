package com.trycatchus.echoo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
import com.trycatchus.echoo.utils.SlugUtils;
import com.trycatchus.echoo.utils.UpdateUtils;

@Service
public class DefaultEventService implements EventService {

    private final LocationRepository locationRepo;
    private final EventRepository eventRepo;
    private final UserRepository userRepo;
    private final EventMapper eventMapper;

    public DefaultEventService(
        LocationRepository locationRepo, 
        EventRepository eventRepo, 
        UserRepository userRepo, 
        EventMapper eventMapper) {
        this.locationRepo = locationRepo;
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.eventMapper = eventMapper;
    }

    private void validateEventConflicts(LocalDateTime startDate, LocalDateTime endDate, UUID locationId, UUID eventIdToExclude) {
        Boolean exists = eventRepo.existsConflictingEvent(
            startDate, endDate, locationId, eventIdToExclude);
        
        if (exists) {
            List<String> uniqueFields = List.of("startDate", "endDate", "locationId");
            throw new UniqueFieldAlreadyInUseException(Event.class, uniqueFields);
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
        return userRepo.findById(UUID.fromString(organizerId))
            .orElseThrow(() -> new EntityNotFoundException(User.class));
    }

    private Location getLocationById(String locationId) {
        return locationRepo.findById(UUID.fromString(locationId))
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

        event.setSlug(SlugUtils.generateUniqueSlug(payload.name()));

        Event savedEvent = eventRepo.save(event);

        return eventMapper.toResponse(savedEvent);
    }

    @Override
    public EventResponse update(String id, EventUpdatePayload payload) {
        Event event = eventRepo.findById(UUID.fromString(id))
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

        Event updatedEvent = eventRepo.save(event);

        return eventMapper.toResponse(updatedEvent);

    }

    @Override
    public void delete(String id) {
        Event event = eventRepo.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Event.class));
        
        validateEventOwnership(event);
        
        eventRepo.delete(event);
    }

    @Override
    public EventResponse findById(String id) {
        Event event = eventRepo.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Event.class));
        
        return eventMapper.toResponse(event);
    }

    @Override
    public List<EventResponse> findAll() {
        List<Event> events = eventRepo.findAll();

        return events.stream()
            .map(eventMapper::toResponse)
            .collect(Collectors.toList());
    }
    
}
