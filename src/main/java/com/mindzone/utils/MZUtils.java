package com.mindzone.utils;

import com.mindzone.entity.Students;
import com.mindzone.repository.StudentRepository;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;

@UtilityClass
public class MZUtils {

    @Autowired
    private StudentRepository repository;

    public long generateId() {

        Students student = repository.findTopByOrderByStudentIdDesc();

        long lastGeneratedId = 0;

        if (student != null) {
            lastGeneratedId = student.getStudentId();
        }
        return ++lastGeneratedId;
    }
}
