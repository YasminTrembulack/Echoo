package com.trycatchus.echoo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycatchus.echoo.model.Participation;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {

}
