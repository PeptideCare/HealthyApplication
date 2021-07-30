package com.healthyapplication.healthyapplication.repository;

import com.healthyapplication.healthyapplication.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, Long> {

    @Query("select t from Title t join fetch t.member m where m.id = :id")
    List<Title> findAllById(@Param("id") String id);
}
