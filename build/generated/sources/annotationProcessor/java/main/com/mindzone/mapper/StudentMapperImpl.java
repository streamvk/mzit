package com.mindzone.mapper;

import com.mindzone.dto.StudentRequestDto;
import com.mindzone.dto.StudentResponseDto;
import com.mindzone.entity.Students;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-26T11:38:56-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.17 (Amazon.com Inc.)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Students toEntity(StudentRequestDto d) {
        if ( d == null ) {
            return null;
        }

        Students.StudentsBuilder students = Students.builder();

        students.studentId( d.getStudentId() );
        students.firstName( d.getFirstName() );
        students.middleName( d.getMiddleName() );
        students.lastName( d.getLastName() );
        students.email( d.getEmail() );
        students.phoneNumber( d.getPhoneNumber() );
        students.grade( d.getGrade() );
        List<String> list = d.getSubjects();
        if ( list != null ) {
            students.subjects( new ArrayList<String>( list ) );
        }
        students.startDate( d.getStartDate() );
        students.endDate( d.getEndDate() );
        students.status( d.getStatus() );

        return students.build();
    }

    @Override
    public StudentResponseDto toDto(Students e) {
        if ( e == null ) {
            return null;
        }

        StudentResponseDto.StudentResponseDtoBuilder<?, ?> studentResponseDto = StudentResponseDto.builder();

        studentResponseDto.studentId( e.getStudentId() );
        studentResponseDto.firstName( e.getFirstName() );
        studentResponseDto.middleName( e.getMiddleName() );
        studentResponseDto.lastName( e.getLastName() );
        studentResponseDto.email( e.getEmail() );
        studentResponseDto.phoneNumber( e.getPhoneNumber() );
        studentResponseDto.grade( e.getGrade() );
        List<String> list = e.getSubjects();
        if ( list != null ) {
            studentResponseDto.subjects( new ArrayList<String>( list ) );
        }
        studentResponseDto.startDate( e.getStartDate() );
        studentResponseDto.endDate( e.getEndDate() );
        studentResponseDto.status( e.getStatus() );

        return studentResponseDto.build();
    }
}
