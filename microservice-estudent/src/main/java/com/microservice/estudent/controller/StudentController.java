package com.microservice.estudent.controller;

import com.microservice.estudent.entities.StudentEntity;
import com.microservice.estudent.service.IStudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final IStudentService studentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@RequestBody StudentEntity student) {
        studentService.save(student);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }
    @GetMapping("/search-by-course/{courseId}")
    public ResponseEntity<?> findByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.findByCourseId(courseId));
    }





}
