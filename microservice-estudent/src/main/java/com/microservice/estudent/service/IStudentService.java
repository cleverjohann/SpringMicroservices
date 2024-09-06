package com.microservice.estudent.service;

import com.microservice.estudent.entities.StudentEntity;

import java.util.List;

public interface IStudentService {

    List<StudentEntity> findAll();
    StudentEntity findById(Long id);
    void save(StudentEntity student);
    List<StudentEntity> findByCourseId(Long courseId);
}
