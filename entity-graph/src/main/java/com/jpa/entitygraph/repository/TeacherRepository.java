package com.jpa.entitygraph.repository;

import com.jpa.entitygraph.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
