package com.trycatchus.echoo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trycatchus.echoo.model.Location;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    @Query("""
    SELECT l FROM Location l
        WHERE l.postalCode = :postalCode
        AND l.complement = :complement
        AND l.number = :number
        AND l.id != :locationIdToExclude
    """)
    Optional<Location> findConflictingLocations(
        String postalCode, 
        String complement, 
        String number,
        UUID locationIdToExclude
    );
}
