package com.healthyapplication.healthyapplication.repository;

import com.healthyapplication.healthyapplication.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {

    @Query("select f from Field f join fetch f.member m where m.id = :id")
    List<Field> findAllById(@Param("id") String id);

}
