package net.falconstudy.api.server.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.falconstudy.api.server.infrastructure.CourseDto;
import net.falconstudy.api.server.infrastructure.PersonResponsibleDto;
import net.falconstudy.api.server.infrastructure.StudentDto;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PersonResponsibleService personResponsibleService;

	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public ResponseEntity<List<StudentDto>> findAll() {
		var students = studentService.findAll(0, 12);
		var studentsDto = StudentDto.from(students);
		return ResponseEntity.ok(studentsDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StudentDto> findById(
			@PathVariable Long id) {
		var student = studentService.findById(id);
		if (student.isPresent()) {
			var studentDto = new StudentDto(student.get());
			return ResponseEntity.ok(studentDto);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<StudentDto> findByIdOrCpfOrRg(
			@RequestParam("searchText") String searchText) {
		var student = studentService.findByIdOrCpfOrRg(searchText);
		if (student.isPresent()) {			
			var studentDto = new StudentDto(student.get());
			return ResponseEntity.ok(studentDto);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/people-responsible")
	public ResponseEntity<List<PersonResponsibleDto>> findPeopleResponsible(
			@PathVariable("id") Long studentId) {
		var peopleResponsible = studentService
				.findPeopleResponsibleById(studentId);
		if (peopleResponsible.size() >= 1) {
			return ResponseEntity.ok(PersonResponsibleDto.from(peopleResponsible));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping("/{studentId}/people-responsible/{personResponsibleId}")
	public ResponseEntity<Void> associateStudentWithPersonResponsible(
			@PathVariable("studentId") Long studentId,
			@PathVariable("personResponsibleId") Long personResponsibleId,
			@RequestBody PersonResponsibleDto personResponsibleDto) {
		var student = studentService.findById(studentId).get();
		var personResponsible = personResponsibleDto.autoConvert();
		personResponsible.setId(personResponsibleId);
		studentService.associate(student, personResponsible);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{studentId}/people-responsible/{personResponsibleId}")
	public ResponseEntity<Void> disassociatePersonResponsible(
			@PathVariable("studentId") Long studentId,
			@PathVariable("personResponsibleId") Long personResponsibleId) {
		var student = studentService.findById(studentId).get();
		var personResponsible = personResponsibleService
				.findById(personResponsibleId)
				.get();
		studentService.disassociate(student, personResponsible);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{studentId}/courses")
	public ResponseEntity<List<CourseDto>> findCourses(
			@PathVariable("studentId") Long studentId) {
		var searchedCourses = studentService.findCoursesByStudentId(studentId);
		if (searchedCourses.isPresent()) {
			var coursesDto = CourseDto.from(searchedCourses.get());
			return ResponseEntity.ok(coursesDto);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{studentId}/courses")
	public ResponseEntity<Void> associateStudentWithStudentCategory(
			@PathVariable("studentId") Long studentId,
			@RequestBody CourseDto courseDto) {
		var searchedStudent = studentService.findById(studentId);
		if (searchedStudent.isPresent()) {
			var student = searchedStudent.get();
			var course = courseDto.autoConvert();
			studentService.associate(student, course);
			
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{studentId}/courses/{courseId}")
	public ResponseEntity<Void> disassociateCourse(
			@PathVariable("studentId") Long studentId,
			@PathVariable("courseId") Long courseId) {
		var student = studentService.findById(studentId).get();
		var course = courseService.findById(courseId).get();
		studentService.disassociate(student, course);
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<StudentDto> save(
			@RequestBody StudentDto studentDto) {
		try {
			var student = studentService.save(studentDto.autoConvert());
			var newStudentDto = new StudentDto(student);
			return new ResponseEntity<>(newStudentDto, HttpStatus.CREATED);
		} catch (StudentAlreadyExistsException exception) {
			return ResponseEntity.noContent().build();
		}
	}

	@PatchMapping()
	/* TODO
	 * Implement validation to not permit the change of 
	 * courses, people responsible andd date of registry */
	public ResponseEntity<StudentDto> patch(
			@RequestBody StudentDto studentDto) {
		var student = studentDto.autoConvert();
		var updatedStudent = studentService.patch(student);
		
		if (updatedStudent.isPresent()) {
			var newStudentDto = new StudentDto(updatedStudent.get());				
			return ResponseEntity.ok(newStudentDto);
		}

		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			studentService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (StudentDoesNotExistsException exception) {
			return ResponseEntity.notFound().build();
		}
	}
}
