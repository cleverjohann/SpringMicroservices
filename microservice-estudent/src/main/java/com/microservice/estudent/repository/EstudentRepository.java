package com.microservice.estudent.repository;

import com.microservice.estudent.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 public interface EstudentRepository extends JpaRepository<StudentEntity, Long> {
    List<StudentEntity> findByCourseId(Long idCurso);
}
