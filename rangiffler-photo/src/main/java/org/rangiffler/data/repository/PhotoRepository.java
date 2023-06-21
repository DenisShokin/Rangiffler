package org.rangiffler.data.repository;

import org.rangiffler.data.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<PhotoEntity, UUID> {

    List<PhotoEntity> findAllByUsername(String username);
}
