package com.healthyapplication.healthyapplication.repository;

import com.healthyapplication.healthyapplication.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
