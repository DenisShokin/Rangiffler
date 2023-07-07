package org.rangiffler.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "photos")
@Data
public class PhotoEntity {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    private UUID id;

    //    @OneToOne(fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    @JoinColumn(name = "id", referencedColumnName = "photo_id", nullable = false)
    @OneToOne(mappedBy = "photo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CountryEntity country;

    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;

    @JsonProperty("description")
    private String description;

    @Column(nullable = false, unique = true)
    private String username;
}
