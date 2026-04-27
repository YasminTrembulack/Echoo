package com.trycatchus.echoo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycatchus.echoo.model.Theme;

public interface ThemeRepository extends JpaRepository<Theme, UUID> {

}
