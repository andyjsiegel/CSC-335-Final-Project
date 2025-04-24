package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// StudentTest
class StudentTest {
    Student s;
    Course c1;

    @BeforeEach
    void setup() {
        s = new Student("user","pass","First","Last","e@mail", false);
        c1 = new Course("Course1","CS101","3","Desc",
            new Instructor("i","p","I","N","e",false),
            new ArrayList<>(List.of(Days.MONDAY, Days.WEDNESDAY)));
    }

    @Test
    void testFullNameAndToString() {
        assertEquals("First Last", s.getFullName());
        assertTrue(s.toString().contains("Student First Last"));
    }

    @Test
    void testAddCourseAndGetGradebook() {
        assertTrue(s.getCourses().isEmpty());
        s.addCourse(c1);
        assertEquals(1, s.getCourses().size());
        StudentGradebook gb = s.getGradebookForCourse(c1);
        assertNotNull(gb);
    }

    @Test
    void testGetCoursesForDay() {
        s.addCourse(c1);
        List<Course> mon = s.getCoursesForDay(Days.MONDAY);
        assertEquals(1, mon.size());
        List<Course> tue = s.getCoursesForDay(Days.TUESDAY);
        assertTrue(tue.isEmpty());
    }

    @Test
    void testComparators() {
        Student s1 = new Student("a","p","Alice","Z","e",true);
        Student s2 = new Student("b","p","Bob","Y","e",true);
        assertTrue(Student.sortByFirstName().compare(s1,s2) < 0);
        assertTrue(Student.sortByLastName().compare(s2,s1) < 0);
        assertTrue(Student.sortByUsername().compare(s1,s2) < 0);
    }
}
