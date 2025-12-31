package com.proj.studentservice.service;

import com.proj.studentservice.payload.PageResponse;
import com.proj.studentservice.payload.StudentDto;
import com.proj.studentservice.payload.StudentResponse;

public interface StudentService {
	StudentResponse createStudent(StudentDto dto);

	StudentResponse updateStudent(Long id, StudentDto dto);

	void enableDisableStudent(Long id);

	StudentResponse getStudentById(Long id);

	PageResponse<StudentResponse> getAllStudents(Boolean isActive,int page, int size);
}
