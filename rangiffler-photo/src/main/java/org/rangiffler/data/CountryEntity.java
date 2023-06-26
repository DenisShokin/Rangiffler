package org.rangiffler.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "countries")
@Data
public class CountryEntity {

    @Id
    @Column(name = "photo_id", nullable = false, columnDefinition = "UUID")
    private UUID photoId;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = true)
    private String name;

}
