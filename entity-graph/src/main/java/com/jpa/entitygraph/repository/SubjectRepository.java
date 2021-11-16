package com.jpa.entitygraph.repository;

import com.jpa.entitygraph.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
