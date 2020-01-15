package net.falconstudy.api.server.application;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import net.falconstudy.api.server.domain.Course;
import net.falconstudy.api.server.infrastructure.CourseRepository;

@Service
public class CourseRestService implements CourseService {

    private CourseRepository studentCourseRepository;

    @Autowired
    public CourseRestService(CourseRepository courseRepository) {
        this.studentCourseRepository = courseRepository;
    }

    @Override
    @CacheEvict(cacheNames = "Company", allEntries = true)
    public Course save(Course course)
            throws StudentAlreadyExistsException {
        return saveOrUpdate(course);
    }

    @Override
    public void saveAll(Collection<Course> categories) {
        studentCourseRepository.saveAll(categories);
    }

    @Override
    @Cacheable(cacheNames = "Company", key = "#id")
    public Optional<Course> findById(Long id) {
        return studentCourseRepository.findById(id);
    }

    @Override
    @Cacheable(cacheNames = "Company", key = "#root.method.name")
    public List<Course> findAll() {
        return studentCourseRepository.findAll();
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return studentCourseRepository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = "Company", key = "#course.getId()")
    public Course update(Course course) throws CourseAlreadyExistsException {
        return saveOrUpdate(course);
    }

    @Override
    @CacheEvict(cacheNames = "Company", key = "#course.getId()")
    public void delete(Course course) {
        deleteById(course.getId());
    }

    @Override
    @CacheEvict(cacheNames = "Company", key = "#course.getId()")
    public void deleteById(Long id) {
        if (studentCourseRepository.findById(id).isEmpty()) {
            throw new CourseDoesNotExistsException(
                    String.format(
                            "Does not exists student course with ID %d.", id));
        }

        studentCourseRepository.deleteById(id);
    }

    @Override
    public long count() {
        return studentCourseRepository.count();
    }

    private Course saveOrUpdate(Course course) {
        var courseAlreadyExists = false;
        if (course.getId() != null && course.getId() != 0) {
            courseAlreadyExists = true;
        }

        if (courseAlreadyExists) {
            throw new StudentAlreadyExistsException(
                    String.format("The course with ID %s already exists.", course.getId()));
        }

        return studentCourseRepository.save(course);
    }

}
