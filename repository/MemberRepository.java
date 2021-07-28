package com.healthyapplication.healthyapplication.repository;

import com.healthyapplication.healthyapplication.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("select m from Member m join fetch m.title join fetch m.image where m.id = :id")
    Member findOne(@Param("id") String id);
}
