package net.falconstudy.api.server.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person_responsible", indexes = {
    @Index(name = "idx_cpf", columnList = "cpf"),
    @Index(name = "idx_rg", columnList = "rg"),
    @Index(name = "idx_email", columnList = "email")
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonResponsible implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id = 0L;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(length = 25, unique = true)
    @CPF
    private String cpf;

    @Column(length = 25, unique = true)
    private String rg;

    @Column(unique = true)
    @Email
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_registry")
    private LocalDate dateOfRegistry;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "person_responsible_student",
            joinColumns = {
                @JoinColumn(name = "person_responsible_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "student_id")})
    private List<Student> children = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        dateOfRegistry = LocalDate.now();
    }

}
