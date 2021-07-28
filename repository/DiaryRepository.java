package com.healthyapplication.healthyapplication.repository;

import com.healthyapplication.healthyapplication.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
