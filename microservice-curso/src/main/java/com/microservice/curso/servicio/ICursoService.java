package com.microservice.curso.servicio;

import com.microservice.curso.entities.CursoEntity;
import com.microservice.curso.http.response.StudentCourseResponse;

import java.util.List;

public interface ICursoService {
    List<CursoEntity> findAll();
    CursoEntity findById(Long id);
    void save (CursoEntity curso);
    StudentCourseResponse findStudentByCourse(Long courseId);
}
