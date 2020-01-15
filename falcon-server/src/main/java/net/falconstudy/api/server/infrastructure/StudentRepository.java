package net.falconstudy.api.server.infrastructure;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.falconstudy.api.server.domain.Course;
import net.falconstudy.api.server.domain.PersonResponsible;
import net.falconstudy.api.server.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentCustomRepository {

	Student findByCpf(String cpf);

	Student findByRg(String rg);

	Page<Student> findByDateOfRegistry(LocalDate dateOfRegistry, Pageable pageRequest);

	@Query("select s.peopleResponsible from Student s where s.id = ?1")
	List<PersonResponsible> findPeopleResponsibleById(Long studentId);

	@Query("select s.courses from Student s where s.id = ?1")
	List<Course> findCoursesByStudentId(Long studentId); 
}
