package com.proj.studentservice.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.proj.studentservice.entity.StudentEntity;
import com.proj.studentservice.exception.NoSuchStudentException;
import com.proj.studentservice.payload.PageResponse;
import com.proj.studentservice.payload.StudentDto;
import com.proj.studentservice.payload.StudentResponse;
import com.proj.studentservice.repository.StudentRepository;
import com.proj.studentservice.service.StudentService;
@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository repo;

	@Override
	public StudentResponse createStudent(StudentDto dto) {
		StudentEntity entity = new StudentEntity();
		entity.setStudentName(dto.getStudentName());
		entity.setStudentEmail(dto.getStudentEmail());
		entity.setStudentMobile(dto.getStudentMobile());
		entity.setCreateDate(LocalDate.now());
		entity.setIsActive(false);
		return map(repo.save(entity));
	}

	@Override
	public StudentResponse updateStudent(Long id, StudentDto dto) {
		StudentEntity entity = repo.findById(id).orElseThrow(() -> new NoSuchStudentException("Student Not Found"));
		entity.setStudentEmail(dto.getStudentEmail());
		entity.setStudentMobile(dto.getStudentMobile());
		entity.setStudentName(dto.getStudentName());
		entity.setUpdateDate(LocalDate.now());
		return map(repo.save(entity));
	}

	@Override
	public void enableDisableStudent(Long id) {
		StudentEntity entity = repo.findById(id).orElseThrow(() -> new NoSuchStudentException("Student Not Found"));
		entity.setIsActive(entity.getIsActive() ? false : true);
		repo.save(entity);
	}

	@Override
	public StudentResponse getStudentById(Long id) {
		StudentEntity entity = repo.findById(id).orElseThrow(() -> new NoSuchStudentException("Student Not Found"));
		return map(entity);
	}

	@Override
	public PageResponse<StudentResponse> getAllStudents(Boolean isActive,int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);

		Page<StudentEntity> student = repo.findByIsActive(isActive,pageable);

		List<StudentResponse> studentResponseList = student.getContent().stream().map(this::map).toList();

		PageResponse<StudentResponse> response = new PageResponse<>();
		response.setContent(studentResponseList);
		response.setPage(page);
		response.setSize(size);
		response.setTotalElements(studentResponseList.size());
		response.setTotalPages(student.getTotalPages());
		return response;
	}

	private StudentResponse map(StudentEntity entity) {
		StudentResponse studentResponse = new StudentResponse();
		studentResponse.setStudentName(entity.getStudentName());
		studentResponse.setStudentEmail(entity.getStudentEmail());
		studentResponse.setStudentMobile(entity.getStudentMobile());
		studentResponse.setIsActive(entity.getIsActive());
		studentResponse.setStudentId(entity.getId());
		return studentResponse;
	}

}
