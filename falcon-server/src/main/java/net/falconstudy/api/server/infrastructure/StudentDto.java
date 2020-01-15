package net.falconstudy.api.server.infrastructure;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.falconstudy.api.server.domain.Student;
import net.falconstudy.api.server.domain.StudentStatus;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class StudentDto {

    @EqualsAndHashCode.Include
    private Long id = 0L;
    private String firstName = "";
    private String lastName = "";
    private String cpf = "";
    private String rg = "";
    private String email = "";
    private String status = StudentStatus.ACTIVE.toString();

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth = LocalDate.now();

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfRegistry = LocalDate.now();

    @JsonIgnore
    private List<CourseDto> courses = new ArrayList<>();
    @JsonIgnore
    private List<PersonResponsibleDto> peopleResponsible = new ArrayList<>();

    ;

    public StudentDto(Student student) {
        id = student.getId();
        firstName = student.getFirstName();
        lastName = student.getLastName();
        cpf = student.getCpf();
        rg = student.getRg();
        email = student.getEmail();
        dateOfBirth = student.getDateOfBirth();
        dateOfRegistry = student.getDateOfRegistry();
        status = student.getStatus().toString();
        courses = List.copyOf(CourseDto.from(student.getCourses()));
        peopleResponsible = PersonResponsibleDto.from(student.getPeopleResponsible());
    }

    public Student autoConvert() {
        var student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setCpf(cpf);
        student.setRg(rg);
        student.setEmail(email);
        student.setDateOfBirth(dateOfBirth);
        student.setDateOfRegistry(dateOfRegistry);
        student.setStatus(StudentStatus.valueOf(status.toString()));

        if (courses != null && coursesIsNotEmpty()) {
            var studentCourses = courses.stream()
                    .map(CourseDto::autoConvert)
                    .collect(Collectors.toList());
            student.setCourses(studentCourses);
        }

        if (peopleResponsible != null && peopleResponsibleIsNotEmpty()) {
            var personResponsible = peopleResponsible.stream()
                    .map(PersonResponsibleDto::autoConvert)
                    .collect(Collectors.toList());
            student.setPeopleResponsible(personResponsible);
        }

        return student;
    }

    public static List<StudentDto> from(List<Student> students) {
        return students != null
                ? students.stream()
                        .map(StudentDto::new)
                        .collect(Collectors.toList())
                : List.of();
    }

    private boolean coursesIsNotEmpty() {
        return !courses.isEmpty();
    }

    private boolean peopleResponsibleIsNotEmpty() {
        return !peopleResponsible.isEmpty();
    }

}
