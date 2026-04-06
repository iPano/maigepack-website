package com.magerpack.backend.repository;

import com.magerpack.backend.model.MediaObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaObjectRepository extends JpaRepository<MediaObject, Long> {
}

