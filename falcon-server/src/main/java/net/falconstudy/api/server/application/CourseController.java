package net.falconstudy.api.server.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.falconstudy.api.server.infrastructure.CourseDto;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService studentCourseService;

	@GetMapping
	public ResponseEntity<List<CourseDto>> getAllCourses() {
		var courses = studentCourseService.findAll();
		return ResponseEntity.ok(CourseDto.from(courses));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CourseDto> findByIdEntity(@PathVariable Long id) {
		var studentCategory = studentCourseService.findById(id);
		if (studentCategory.isPresent()) {
			var studentCategoryDto = new CourseDto(studentCategory.get());
			return ResponseEntity.ok(studentCategoryDto);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<CourseDto> addCourse(
			@RequestBody CourseDto category) {
		try {
			var newCourse = studentCourseService.save(category.autoConvert());
			return new ResponseEntity<>(new CourseDto(newCourse), 
					HttpStatus.CREATED);
		} catch (CourseAlreadyExistsException exception) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CourseDto> update(
			@PathVariable Long id,
			@RequestBody CourseDto studentCategoryDto) {
		studentCategoryDto.setId(id);
		try {
			var updatedStudentCategory = studentCourseService.update(
					studentCategoryDto.autoConvert());
			var updateStudentCategoryModel = new CourseDto(updatedStudentCategory);
			return ResponseEntity.ok(updateStudentCategoryModel);
		} catch (StudentAlreadyExistsException exception) {
			return ResponseEntity.noContent().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		try {
			studentCourseService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (CourseDoesNotExistsException exception) {
			return ResponseEntity.notFound().build();
		}
	}

}
