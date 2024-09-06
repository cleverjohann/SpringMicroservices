package com.microservice.estudent.service;

import com.microservice.estudent.entities.StudentEntity;
import com.microservice.estudent.repository.EstudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService{
    private EstudentRepository studentRepository;

    @Override
    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public StudentEntity findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public void save(StudentEntity student) {
        studentRepository.save(student);
    }

    @Override
    public List<StudentEntity> findByCourseId(Long courseId) {
        return studentRepository.findByCourseId(courseId);
    }
}
