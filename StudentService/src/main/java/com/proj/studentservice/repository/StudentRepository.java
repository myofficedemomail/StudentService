package com.proj.studentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.studentservice.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {
	Page<StudentEntity> findByIsActive(Boolean isActive, Pageable pageable);
}
