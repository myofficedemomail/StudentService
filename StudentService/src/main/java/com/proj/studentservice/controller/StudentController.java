package com.proj.studentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.studentservice.payload.PageResponse;
import com.proj.studentservice.payload.StudentDto;
import com.proj.studentservice.payload.StudentResponse;
import com.proj.studentservice.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/students")
@Tag(name = "Student APIs", description = "Student reistration management APIs")
public class StudentController {
	@Autowired
	private StudentService service;

	@Operation(summary = "Register student")
	@PostMapping
	public ResponseEntity<StudentResponse> create(@RequestBody StudentDto dto) {
		return ResponseEntity.ok(service.createStudent(dto));
	}

	@Operation(summary = "Get student by id")
	@GetMapping("/{id}")
	public ResponseEntity<StudentResponse> get(@PathVariable Long id) {
		return ResponseEntity.ok(service.getStudentById(id));
	}

	@Operation(summary = "Get all students with pagination")
	@GetMapping
	public ResponseEntity<PageResponse<StudentResponse>> getAll(@RequestParam(required = false) Boolean isActive,@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		return ResponseEntity.ok(service.getAllStudents(isActive,page, size));
	}

	@Operation(summary = "Update student")
	@PutMapping("/{studentId}")
	public ResponseEntity<StudentResponse> update(@PathVariable Long studentId, @RequestBody StudentDto dto) {
		return ResponseEntity.ok(service.updateStudent(studentId, dto));
	}

	@Operation(summary = "Enable or disable student")
	@PutMapping("/enableDisableStudent")
	public ResponseEntity<String> enableDisableStudent(@RequestParam Long studentId) {

		service.enableDisableStudent(studentId);
		return ResponseEntity.ok("Student status updated");
	}
}
