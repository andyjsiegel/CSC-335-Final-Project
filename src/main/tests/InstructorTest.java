package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// InstructorTest
class InstructorTest {
    Instructor inst;
    Course c;

    @BeforeEach
    void setup() {
        inst = new Instructor("u","p","I","N","e",false);
        c = new Course("Name","C1","3","Desc",inst,new ArrayList<>());
    }

    @Test
    void testFullNameAndToString() {
        assertEquals("I N", inst.getFullName());
        assertTrue(inst.toString().contains("Instructor I N"));
    }

    @Test
    void testAddCourseAndGetCoursesForDay() {
        inst.addCourse(c);
        assertEquals(1, inst.getCourses().size());
        // no days yet, so empty
        assertTrue(inst.getCoursesForDay(Days.MONDAY).isEmpty());
        c.addDays(new ArrayList<>(List.of(Days.MONDAY)));
        assertEquals(1, inst.getCoursesForDay(Days.MONDAY).size());
    }
}
