package net.falconstudy.api.server.application;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.falconstudy.api.server.domain.Course;
import net.falconstudy.api.server.domain.PersonResponsible;
import net.falconstudy.api.server.domain.Student;
import net.falconstudy.api.server.domain.StudentStatus;
import net.falconstudy.api.server.infrastructure.StudentRepository;

@Service
public class StudentRestService implements StudentService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private PersonResponsibleService personResponsibleService;
	
	@Override
	public Student save(Student student) throws StudentAlreadyExistsException {
		return saveOrUpdate(student);
	}
	
	@Override
	/* TODO throws correct exceptions */
	public void associate(Student student, PersonResponsible associablePersonResponsible) {
		var registeredStudent = findById(student.getId()).get();
		var personResponsibleNotExists = registeredStudent
				.getPeopleResponsible()
				.stream()
				.noneMatch(
						existingPersonResponsible -> 
						existingPersonResponsible.getId().equals(associablePersonResponsible.getId()));
	
		if (personResponsibleNotExists) {
			var personResponsible = personResponsibleService
				.findById(associablePersonResponsible.getId())
				.get();
			registeredStudent.addPersonResponsible(personResponsible);
			studentRepository.save(registeredStudent);
		}
	}
	
	@Override
	/* TODO throws correct exceptions */
	public void disassociate(Student student, PersonResponsible disassociablePersonResponsible) {
		var registeredStudent = findById(student.getId()).get();
		var personResponsibleAlreadyExists = registeredStudent.getCourses()
				.stream()
				.anyMatch(
					existingPersonResponsible -> 
						existingPersonResponsible.getId().equals(disassociablePersonResponsible.getId()));
		
		if (personResponsibleAlreadyExists) {
			var personResponsible = personResponsibleService
					.findById(disassociablePersonResponsible.getId())
					.get();
			registeredStudent.removePersonResponsible(personResponsible);
			studentRepository.save(registeredStudent);
		}
	}
	
	@Override
	/* TODO throws correct exceptions */
	public void associate(Student student, Course associableCourse) {
		var registeredStudent = findById(student.getId()).get();
		var courseNotExists = registeredStudent.getCourses()
				.stream()
				.noneMatch(
					existingCourse -> 
						existingCourse.getId().equals(associableCourse.getId()));
		
		if (courseNotExists) {
			var studentCategory = courseService
				.findById(associableCourse.getId())
				.get();
			registeredStudent.addCourse(studentCategory);
			studentRepository.save(registeredStudent);
		}
	}
	
	@Override
	public void disassociate(Student student, Course disassociableCourse) {
		var registeredStudent = findById(student.getId()).get();
		var courseAlreadyAssociated = registeredStudent.getCourses()
				.stream()
				.anyMatch(
					existingCourse -> 
						existingCourse.getId().equals(disassociableCourse.getId()));
		
		if (courseAlreadyAssociated) {
			var course = courseService
				.findById(disassociableCourse.getId())
				.get();
			registeredStudent.removeCourse(course);
			studentRepository.save(registeredStudent);
		}
	}
	
	@Override
	public Optional<Student> findById(Long id) {
		return studentRepository.findById(id);
	}

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	@Override
	public List<Student> findAll(int offset, int maxResults) {
		var queryText = "SELECT DISTINCT new net.falconstudy.api.server.domain.Student("
				+ "s.id, s.firstName, s.lastName, s.cpf, s.rg, s.email, s.dateOfBirth, s.dateOfRegistry, s.status) FROM Student s "
				+ "ORDER BY s.firstName";
		var jpqlQuery = entityManager.createQuery(queryText, Student.class)
				.setMaxResults(maxResults)
				.setFirstResult(offset);
		return jpqlQuery.getResultList();
	}
	
	@Override
	public Page<Student> findAll(Pageable pageable) {
		return studentRepository.findAll(pageable);
	}
	
	@Override
	public Optional<Student> findByIdOrCpfOrRg(String searchText) {
		return studentRepository.findByIdOrCpfOrRg(searchText);
	}
	
	@Override
	public List<PersonResponsible> findPeopleResponsibleById(Long studentId) {
		return studentRepository.findPeopleResponsibleById(studentId);
	}
	
	@Override
	public Optional<List<Course>> findCoursesByStudentId(Long studentId) {
		return Optional.ofNullable(studentRepository.findCoursesByStudentId(studentId));
	}
	
	@Override
	public Student update(Student student) {
		return studentRepository.save(student);
	}
	
	@Override
	public Optional<Student> patch(Student student) {
		var searchedStudent = studentRepository.findStudentById(student.getId());
		Student studentUpdated = null;
		if (searchedStudent.isPresent()) {
			studentUpdated = searchedStudent.get();
			studentUpdated.setFirstName(student.getFirstName());
			studentUpdated.setLastName(student.getLastName());
			studentUpdated.setEmail(student.getEmail());
			studentUpdated.setCpf(student.getCpf());
			studentUpdated.setRg(student.getRg());
			studentUpdated.setDateOfBirth(student.getDateOfBirth());
			studentUpdated.setStatus(student.getStatus());
			return Optional.ofNullable(studentRepository.save(studentUpdated));
		}
		
		throw new StudentDoesNotExistsException(
				String.format("The student with ID %d does not exists.", 
						student.getId()));		
	}
	
	@Override
	public void delete(Student student) {
		student.setStatus(StudentStatus.INACTIVE);
	}
	
	@Override
	public void deleteById(Long id) throws StudentDoesNotExistsException {
		var searchedStudent = findById(id);
		if (searchedStudent.isPresent()) {
			delete(searchedStudent.get());
		} else {
			throw new StudentDoesNotExistsException(
					String.format("The student with ID %d does not exists.", 
							id));
		}
	}
	
	@Override
	public long count() {
		return studentRepository.count();
	}
	
	private Student saveOrUpdate(Student student) {
		boolean studentAlreadyExists = true;
		var registeredStudent = studentRepository.save(student);
		if (student.getId() != null 
				&& studentRepository.existsById(student.getId())) {
			studentAlreadyExists = false;
		}
		
		if (studentAlreadyExists) {
			throw new StudentAlreadyExistsException(
					String.format(
							"The student with ID %s already exists.", 
							student.getId()));
		
		}
				
		return registeredStudent;
	}
		
}
