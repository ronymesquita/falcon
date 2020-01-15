package net.falconstudy.api.server.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "student", indexes = {
    @Index(name = "idx_cpf", columnList = "cpf"),
    @Index(name = "idx_rg", columnList = "rg"),
    @Index(name = "idx_email", columnList = "email")
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id = 0L;

    @Column(name = "first_name", nullable = false)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    @CPF
    private String cpf;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min = 8)
    private String rg;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "date_of_registry", nullable = false)
    private LocalDate dateOfRegistry;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StudentStatus status = StudentStatus.ACTIVE;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses = new ArrayList<>();

    @ManyToMany(mappedBy = "children")
    private List<PersonResponsible> peopleResponsible = new ArrayList<>();

    public Student(Long id,
            @NotBlank String firstName,
            String lastName,
            @CPF String cpf,
            @NotBlank @Size(min = 8) String rg,
            @Email String email,
            LocalDate dateOfBirth,
            LocalDate dateOfRegistry,
            StudentStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistry = dateOfRegistry;
        this.status = status;
    }

    public void addPersonResponsible(PersonResponsible personResponsible) {
        peopleResponsible.add(personResponsible);
        personResponsible.getChildren().add(this);
    }

    public void removePersonResponsible(PersonResponsible personResponsible) {
        peopleResponsible.remove(personResponsible);
        personResponsible.getChildren().remove(this);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.getStudents().remove(this);
    }

    @PrePersist
    private void prePersist() {
        dateOfRegistry = LocalDate.now();
    }

}
