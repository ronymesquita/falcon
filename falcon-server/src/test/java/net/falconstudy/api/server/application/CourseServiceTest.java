package net.falconstudy.api.server.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import net.falconstudy.api.server.domain.Course;
import net.falconstudy.api.server.domain.Student;
import net.falconstudy.api.server.infrastructure.CourseRepository;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @TestConfiguration
    static class TestConfigurationBeans {

        @Bean
        public CourseService courseService(@Autowired CourseRepository courseRepository) {
            return new CourseRestService(courseRepository);
        }
    }

    @Mock
    CourseService courseService;

    @Test
    void whenSaveValidCourseShouldWorks() {
        var course = new Course(0L, "", "", List.<Student>of());
        when(courseService.save(course)).thenReturn(course);

        assertThat(courseService.save(course), equalTo(course));
    }

    @Test
    void whenSaveValidCourseShouldReturnValidId() {
        var course = new Course(0L, "", "", List.<Student>of());
        when(courseService.save(course)).thenAnswer(invocationMock -> {
            var registeredCourse = (Course) invocationMock.getArgument(0);
            registeredCourse.setId(1L);
            return registeredCourse;
        });

        var savedCourse = courseService.save(course);
        verify(courseService).save(course);
        assertThat(savedCourse.getId(), is(Matchers.greaterThan(0L)));
    }
}
