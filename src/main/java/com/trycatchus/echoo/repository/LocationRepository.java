package com.trycatchus.echoo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycatchus.echoo.model.Location;

public interface LocationRepository extends JpaRepository<Location, UUID> {

}
