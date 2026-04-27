package com.trycatchus.echoo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycatchus.echoo.model.EventTheme;

public interface EventThemeRepository extends JpaRepository<EventTheme, UUID> {

}
