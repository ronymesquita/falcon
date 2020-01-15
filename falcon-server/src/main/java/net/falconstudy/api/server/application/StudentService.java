package net.falconstudy.api.server.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.falconstudy.api.server.domain.Course;
import net.falconstudy.api.server.domain.PersonResponsible;
import net.falconstudy.api.server.domain.Student;

public interface StudentService {

	Student save(Student student) throws StudentAlreadyExistsException;

	void associate(Student student, PersonResponsible associablePersonResponsible);
	
	void disassociate(Student student, PersonResponsible disassociablePersonResponsible);
	
	void associate(Student student, Course associableCourse);
	
	void disassociate(Student student, Course disassociableCourse);
	
	Optional<Student> findById(Long id);
	
	List<Student> findAll(int maxResults, int offset);
	
	List<Student> findAll();

	Page<Student> findAll(Pageable pageable);
	
	Optional<Student> findByIdOrCpfOrRg(String searchText);
	
	List<PersonResponsible> findPeopleResponsibleById(Long studentId);
	
	Optional<List<Course>> findCoursesByStudentId(Long studentId);

	Student update(Student student);
	
	Optional<Student> patch(Student student);

	void delete(Student student) throws StudentDoesNotExistsException;

	void deleteById(Long id) throws StudentDoesNotExistsException;

	long count();
	
}