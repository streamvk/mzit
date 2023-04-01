package com.mindzone.service.impl;

import com.mindzone.dto.StudentRequestDto;
import com.mindzone.dto.StudentResponseDto;
import com.mindzone.entity.Students;
import com.mindzone.exception.UserNotFoundException;
import com.mindzone.mapper.StudentMapper;
import com.mindzone.repository.StudentRepository;
import com.mindzone.service.StudentService;
import com.mindzone.utils.MZUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private MZUtils mzUtils;


    @Override
    public StudentResponseDto create(StudentRequestDto studentRequestDto) {

        Students student = studentMapper.toEntity(studentRequestDto);
        //Change generate id logic as required.
        long studentId = mzUtils.generateId();
        student.setStudentId(studentId);

        Students savedStudent = repository.save(student);
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public StudentResponseDto update(StudentRequestDto studentRequestDto) {

        Students student = studentMapper.toEntity(studentRequestDto);
        Students savedUser = null;
        try {
            Students foundStudent = repository.findByStudentId(studentRequestDto.getStudentId()).orElseThrow(() -> new UserNotFoundException("Student not found by studentId:" + studentRequestDto.getStudentId()));
            if (nonNull(foundStudent)) {
                foundStudent.setFirstName(student.getFirstName());
                foundStudent.setMiddleName(student.getMiddleName());
                foundStudent.setLastName(student.getLastName());
                foundStudent.setEmail(student.getEmail());
                foundStudent.setGrade(student.getGrade());
                foundStudent.setPhoneNumber(student.getPhoneNumber());
                foundStudent.setStartDate(student.getStartDate());
                foundStudent.setEndDate(student.getEndDate());
                foundStudent.setStatus(student.getStatus());
                foundStudent.setSubjects(student.getSubjects());
                savedUser = repository.save(foundStudent);
            }
        } catch (Exception ex) {
            log.error("Exception occurred in update method..!!"+ex.getMessage());
        }
        return studentMapper.toDto(savedUser);
    }

    @Override
    public void delete(long studentId) {
        try {
            Students user = repository.findByStudentId(studentId).orElseThrow(() -> new UserNotFoundException("User not found by studentId:" + studentId));
            repository.delete(user);
        }catch(UserNotFoundException ex){
            log.error("Exception in delete method..!!",ex.getMessage());
        }
    }

    @Override
    public StudentResponseDto get(long studentId) {
        Students user = repository.findByStudentId(studentId).orElseThrow(() -> new UserNotFoundException("User not found by studentId:" + studentId));
        return studentMapper.toDto(user);
    }

    @Override
    public List<StudentResponseDto> getAll() {
        List<Students> students = repository.findAll(Sort.by(Sort.Direction.ASC,"studentId"));
        return students.stream().map(student -> studentMapper.toDto(student)).collect(Collectors.toList());
    }

}
