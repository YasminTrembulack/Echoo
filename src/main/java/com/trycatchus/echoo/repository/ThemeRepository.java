package com.trycatchus.echoo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trycatchus.echoo.models.Theme;

public interface ThemeRepository extends JpaRepository<Theme, UUID> {
    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM Theme t
        WHERE t.name = :name
        AND (:themeIdToExclude IS NULL OR t.id <> :themeIdToExclude)
    """)
    Boolean existsConflictingTheme(
        String name,
        UUID themeIdToExclude
    );

}
