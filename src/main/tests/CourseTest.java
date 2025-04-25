// Tests all non-view related methods, setters, and getters


package main.tests;

import main.model.*;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course course;
    private Instructor instructor;
    private List<Days> days;

    @BeforeEach
    void setUp() {
        instructor = new Instructor("u1", "pw", "Alice", "Anderson", "a@x.com", false);
        days = Arrays.asList(Days.MONDAY, Days.FRIDAY);
        course = new Course(
            "Intro to Testing",
            "TEST101",
            "A course on how to test Java code",
            instructor,
            new ArrayList<>(days)
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Intro to Testing", course.getName());
        assertEquals("TEST101",         course.getCourseCode());
        assertEquals("A course on how to test Java code", course.getDescription());
        assertSame(instructor,          course.getInstructor());
        assertEquals(days,              course.getDays());
        // mutable copy
        assertNotSame(days,             course.getDays());
    }

    @Test
    void testStudentListManipulation() {
        assertTrue(course.getStudents().isEmpty());
        Student s = new Student("stu", "pw", "Bob", "Brown", "b@x.com", false);
        course.addStudent(s);
        assertEquals(1, course.getStudents().size());
        assertSame(s, course.getStudents().get(0));
        course.removeStudent(s);
        assertTrue(course.getStudents().isEmpty());
    }

    @Test
    void testAssignmentListManipulation() {
        CourseAssignments ca = course.getAssignments();
        assertEquals(0, ca.size());

        Assignment a = new Assignment("HW1", "desc", 10);
        course.addAssignment(a);
        assertEquals(1, ca.size());
        course.removeAssignment(a);
        assertEquals(0, ca.size());
    }

    @Test
    void testAddDaysReplacesList() {
        List<Days> newDays = Collections.singletonList(Days.WEDNESDAY);
        course.addDays(new ArrayList<>(newDays));
        assertEquals(newDays, course.getDays());
    }

    @Test
    void testCopyConstructorProducesDeepCopy() {
        // seed some state
        course.addStudent(new Student("s1","pw","C","C","c@x.com",false));
        course.addAssignment(new Assignment("T1","d",5));
        course.setDefaultCategories();

        Course copy = new Course(course);
        // identity
        assertNotSame(course, copy);
        // simple fields
        assertEquals(course.getName(),        copy.getName());
        assertEquals(course.getCourseCode(),  copy.getCourseCode());
        assertEquals(course.getDescription(), copy.getDescription());
        // instructor is newly constructed
        assertNotSame(course.getInstructor(), copy.getInstructor());
    }
    
}
