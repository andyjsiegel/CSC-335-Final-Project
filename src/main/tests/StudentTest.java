package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// StudentTest
class StudentTest {
    Student s;
    Course c1;

    @BeforeEach
    void setup() {
        s = new Student("user","pass","First","Last","e@mail", false);
        c1 = new Course("Course1","CS101","Desc",
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
    
    @Test
    void testCopyConstructorCopiesUserFieldsOnly() {
        s.addCourse(c1);
        Student copy = new Student(s);
        // User fields should match
        assertEquals(s.getUsername(), copy.getUsername());
        assertEquals(s.getFirstName(),  copy.getFirstName());
        assertEquals(s.getLastName(),   copy.getLastName());
        assertEquals(s.getEmail(),      copy.getEmail());
        // but course lists should be independent (copy's list is not the same object)
        assertNotSame(s.getCourses(), copy.getCourses());
        // since copy constructor doesn't add courses, its list should be empty
        assertTrue(copy.getCourses().isEmpty());
    }

    @Test
    void testGetAllAssignmentsAndGetAssignmentByName() {
        // no gradebooks yet → empty
        assertTrue(s.getAllAssignments().isEmpty());
        // add a course → gradebook created
        s.addCourse(c1);
        StudentGradebook gb = s.getGradebookForCourse(c1);
        // add two assignments
        Assignment a1 = new Assignment("HW1","d",10);
        Assignment a2 = new Assignment("HW2","d",20);
        gb.addAssignment(a1, 5.0);
        gb.addAssignment(a2,15.0);

        List<Assignment> all = s.getAllAssignments();
        assertEquals(2, all.size());
        // getAssignmentByName
        Assignment found = s.getAssignmentByName("HW2");
        assertNotNull(found);
        assertEquals("HW2", found.getTitle());
        assertNull(s.getAssignmentByName("NONEXISTENT"));
    }

    @Test
    void testSortByGradeOnAssignmentWithMissingAndPresent() {
        Course c2 = new Course("Course2","CS102","Desc",
            new Instructor("i","p","I","N","e",false),
            new ArrayList<>(List.of(Days.TUESDAY)));
        s.addCourse(c1);
        Student other = new Student("u2","pw","X","Y","e",false);
        other.addCourse(c1);

        // grade only s on "G"
        StudentGradebook gb = s.getGradebookForCourse(c1);
        gb.addAssignment(new Assignment("G","d",10), 8.0);

        Comparator<Student> cmp = Student.sortByGradeOnAssignment("G");
        // s has 0.8, other has -1
        assertTrue(cmp.compare(other, s) < 0);
        // both missing on "Z"
        Comparator<Student> cmpZ = Student.sortByGradeOnAssignment("Z");
        assertEquals(0, cmpZ.compare(s, other));
    }

    @Test
    void testSetFinalGradeForCourseValidAndInvalid() throws Exception {
        // add course so branch index!=-1
        s.addCourse(c1);
        StudentGradebook gb = s.getGradebookForCourse(c1);

        // valid grade strings
        s.setFinalGradeForCourse(c1,"A");
        // we need to peek into the private finalGrade field since getFinalGrade() recomputes
        Field fgField = StudentGradebook.class.getDeclaredField("finalGrade");
        fgField.setAccessible(true);
        assertEquals(FinalGrades.A, fgField.get(gb));

        // lowercase and whitespace
        s.setFinalGradeForCourse(c1,"  b  ");
        assertEquals(FinalGrades.B, fgField.get(gb));

        // invalid string → should catch and leave previous value untouched
        s.setFinalGradeForCourse(c1,"Z");
        assertEquals(FinalGrades.B, fgField.get(gb));
    }

    @Test
    void testSetFinalGradeForCourseCourseNotFound() {
        // never added c1 → index == -1 branch
        assertDoesNotThrow(() -> s.setFinalGradeForCourse(c1, "C"));
        // nothing to assert beyond "no exception"
    }
}
