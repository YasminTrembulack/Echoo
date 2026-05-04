package com.trycatchus.echoo.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trycatchus.echoo.model.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query("""
        SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
        FROM Event e
        WHERE e.location.id = :locationId
        AND (:eventIdToExclude IS NULL OR e.id <> :eventIdToExclude)
        AND e.startDate < :endDate
        AND e.endDate > :startDate
    """)
    Boolean existsConflictingEvent(
        LocalDateTime startDate,
        LocalDateTime endDate,
        UUID locationId,
        UUID eventIdToExclude
    );
}
