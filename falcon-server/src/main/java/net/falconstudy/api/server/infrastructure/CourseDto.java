package net.falconstudy.api.server.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.falconstudy.api.server.domain.Course;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CourseDto {

    @EqualsAndHashCode.Include
    private Long id = 0L;
    private String name = "";
    private String description = "";

    public CourseDto(Course course) {
        id = course.getId();
        name = course.getName();
        description = course.getDescription();
    }

    public Course autoConvert() {
        var course = new Course();
        course.setId(id);
        course.setName(name);
        course.setDescription(description);
        return course;
    }

    public static List<CourseDto> from(List<Course> courses) {
        return courses != null
                ? courses.stream()
                        .map(CourseDto::new)
                        .collect(Collectors.toList())
                : List.of();
    }

}
