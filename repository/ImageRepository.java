package com.healthyapplication.healthyapplication.repository;

import com.healthyapplication.healthyapplication.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByName(@Param("name") String name);
}
