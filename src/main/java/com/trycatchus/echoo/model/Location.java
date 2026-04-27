package com.trycatchus.echoo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "locations", uniqueConstraints = {
        @UniqueConstraint(name = "unique_location", columnNames = {
                "number",
                "complement",
                "postal_code"
        })
})
public class Location extends BaseEntity {

    @Column(length = 150)
    private String establishment;

    @Column(length = 150, nullable = false)
    private String street;

    @Column(length = 20)
    private String number;

    @Column(length = 100)
    private String complement;

    @Column(length = 100, nullable = false)
    private String neighborhood;

    @Column(length = 100, nullable = false)
    private String city;

    @Column(length = 100, nullable = false)
    private String state;

    @Column(length = 100, nullable = false)
    private String country;

    @Column(name = "postal_code", length = 20, nullable = false)
    private String postalCode;

}