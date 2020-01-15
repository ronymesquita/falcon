package net.falconstudy.api.server.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import net.falconstudy.api.server.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
