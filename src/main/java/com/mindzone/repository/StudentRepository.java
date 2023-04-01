package com.mindzone.repository;

import com.mindzone.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Students, UUID> {

    Optional<Students> findByStudentId(Long studentId);
    Students findTopByOrderByStudentIdDesc();
}
