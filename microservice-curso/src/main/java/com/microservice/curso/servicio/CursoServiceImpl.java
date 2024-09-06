package com.microservice.curso.servicio;

import com.microservice.curso.client.EstudentClient;
import com.microservice.curso.dto.StudentDto;
import com.microservice.curso.entities.CursoEntity;
import com.microservice.curso.http.response.StudentCourseResponse;
import com.microservice.curso.repository.CursoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CursoServiceImpl implements ICursoService {


    private final CursoRepository cursoRepository;

    private final EstudentClient estudentClient;

    @Override
    public List<CursoEntity> findAll() {
        return (List<CursoEntity>) cursoRepository.findAll();
    }

    @Override
    public CursoEntity findById(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    @Override
    public void save(CursoEntity curso) {
        cursoRepository.save(curso);
    }

    @Override
    public StudentCourseResponse findStudentByCourse(Long courseId) {
        //Consultar el curso
        CursoEntity curso = cursoRepository.findById(courseId).orElse(null);
        List<StudentDto> students = estudentClient.findAllStudentByCourse(courseId);

        assert curso != null;
        return StudentCourseResponse.builder()
                .courseName(curso.getName())
                .teacher(curso.getTeacher())
                .students(students)
                .build();
    }
}
