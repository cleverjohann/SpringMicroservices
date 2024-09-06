package com.microservice.curso.http.response;

import com.microservice.curso.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCourseResponse {
    private  String courseName;
    private String teacher;
    private List<StudentDto> students;
}
