package com.mindzone.controller;

import com.mindzone.dto.StudentRequestDto;
import com.mindzone.dto.StudentResponseDto;
import com.mindzone.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mindzone.utils.MZConstants.STUDENTS;
import static com.mindzone.utils.MZConstants.URI;

@RestController
@RequestMapping(path = URI+ STUDENTS)
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService service;


    @PostMapping
    public @ResponseBody StudentResponseDto create(@RequestBody StudentRequestDto studentRequestDto){
    return service.create(studentRequestDto);
    }

    @PutMapping
    public @ResponseBody StudentResponseDto update(@RequestBody StudentRequestDto studentRequestDto){
        return service.update(studentRequestDto);
    }

    @GetMapping(path ="/{studentId}")
    public @ResponseBody StudentResponseDto get(@PathVariable long studentId){
        return service.get(studentId);
    }
    @GetMapping(path ="/all")
    public @ResponseBody List<StudentResponseDto> get(){
        return service.getAll();
    }
    @DeleteMapping(path ="/{studentId}")
    public void delete(@PathVariable long studentId){
         service.delete(studentId);
    }

}
