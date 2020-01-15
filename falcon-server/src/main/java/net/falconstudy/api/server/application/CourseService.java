package net.falconstudy.api.server.application;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.falconstudy.api.server.domain.Course;

public interface CourseService {

	Course save(Course course) throws CourseAlreadyExistsException;
	
	void saveAll(Collection<Course> courses);
	
	Optional<Course> findById(Long id);
	
	List<Course> findAll();
	
	Page<Course> findAll(Pageable pageable);
	
	Course update(Course course) throws CourseAlreadyExistsException;
	
	void delete(Course course) throws CourseDoesNotExistsException;
	
	public void deleteById(Long id) throws CourseDoesNotExistsException;

	long count();

}
