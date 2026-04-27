package com.trycatchus.echoo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "themes")
public class Theme extends BaseEntity {

    @Column(length = 30, unique = true, nullable = false)
    private String name;

    @Column(length = 255, nullable = true)
    private String description;

}