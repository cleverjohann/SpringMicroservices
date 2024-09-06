package com.microservice.curso.controller;

import com.microservice.curso.entities.CursoEntity;
import com.microservice.curso.servicio.ICursoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
public class CursoController {

    private final ICursoService cursoService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@RequestBody CursoEntity curso) {
        cursoService.save(curso);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.findById(id));
    }

    @GetMapping("/search-student/{courseId}")
    public ResponseEntity<?> findStudentByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(cursoService.findStudentByCourse(courseId));
    }


}
