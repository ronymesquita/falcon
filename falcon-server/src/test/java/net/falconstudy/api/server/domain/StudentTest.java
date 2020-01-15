package net.falconstudy.api.server.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {

    private Student student;
    
    @BeforeEach
    void init() {
        student = new Student();
    }
    
    @Test
    void whenInstantiatedThenIdIs0() {
        assertEquals(0L, student.getId());
    }
    
    @Test
    void whenInstantiatedThenAreNoCourses() {
        assertTrue(student.getCourses().isEmpty());
    }
    
    @Test
    void whenAddOneCourseShouldWorks() throws Exception {
        var course = new Course();
        student.addCourse(course);
        assertThat(student.getCourses(), is(notNullValue()));
    }
    
    @Test
    void whenAddManyCoursesShouldWorks() {
        var course1 = new Course();
        var course2 = new Course();
        var course3 = new Course();
        student.addCourse(course1);
        student.addCourse(course2);
        student.addCourse(course3);
        assertThat(student.getCourses(), hasSize(3));
    }
    
    @Test
    void whenRemoveCoursesShouldWorks() {
        var course1 = new Course();
        var course2 = new Course();
        var course3 = new Course();
        student.addCourse(course1);
        student.addCourse(course2);
        student.addCourse(course3);
        student.removeCourse(course1);
        student.removeCourse(course2);
        student.removeCourse(course3);
        assertThat(student.getCourses(), is(empty()));
    }
    
    @Test
    void whenInstantiatedThenAreNoPeopleResponsible() {
        assertTrue(student.getPeopleResponsible().isEmpty());
    }
    
    @Test
    void whenAddPersonResponsibleShouldWorks() {
        var personResponsible = new PersonResponsible();
        student.addPersonResponsible(personResponsible);
        assertNotNull(student.getPeopleResponsible().get(0));
    }
    
    @Test
    void whenAdd2PeopleResponsibleShouldWorks() {
        var personResponsible1 = new PersonResponsible();
        var personResponsible2 = new PersonResponsible();
        student.addPersonResponsible(personResponsible1);
        student.addPersonResponsible(personResponsible2);
        assertThat(student.getPeopleResponsible(), hasSize(2));
    }
    
    @Test
    void whenRemovePeopleResponsibleShouldWorks() {
        var personResponsible1 = new PersonResponsible();
        var personResponsible2 = new PersonResponsible();
        student.addPersonResponsible(personResponsible1);
        student.addPersonResponsible(personResponsible2);
        student.removePersonResponsible(personResponsible1);
        student.removePersonResponsible(personResponsible2);
        assertThat(student.getPeopleResponsible(), is(empty()));
    }

}
