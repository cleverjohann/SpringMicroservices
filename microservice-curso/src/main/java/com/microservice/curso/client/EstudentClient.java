package com.microservice.curso.client;

import com.microservice.curso.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-student",url = "localhost:8041")
public interface EstudentClient {

    @GetMapping("/api/students/search-by-course/{courseId}")
    List<StudentDto> findAllStudentByCourse(@PathVariable Long courseId);
}
