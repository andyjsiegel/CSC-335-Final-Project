package main.tests;

import main.model.*;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import javax.swing.*;

// CourseTest
class CourseTest {
    Course course;
    Instructor inst;
    List<Days> days;

    @BeforeEach
    void setup() {
        inst = new Instructor("u","p","I","N","e",false);
        days = new ArrayList<>(List.of(Days.TUESDAY));
        course = new Course("Name","C101","3","Desc", inst, days);
    }

    @Test
    void testBasicGetters() {
        assertEquals("Name", course.getName());
        assertEquals("C101", course.getCourseCode());
        assertEquals("Desc", course.getDescription());
        assertSame(inst, course.getInstructor());
        assertEquals(days, course.getDays());
    }

    @Test
    void testAddRemoveStudentAndAssignment() {
        Student s = new Student("u","p","F","L","e",true);
        course.addStudent(s);
        assertTrue(course.getStudents().contains(s));
        course.removeStudent(s);
        assertFalse(course.getStudents().contains(s));

        Assignment a = new Assignment("T","D",10);
        course.addAssignment(a);
        assertTrue(course.getAssignments().iterator().hasNext());
        course.removeAssignment(a);
        assertFalse(course.getAssignments().iterator().hasNext());
    }

    @Test
    void testAddDaysAndDefaultCategories() {
        List<Days> newDays = List.of(Days.MONDAY);
        course.addDays(new ArrayList<>(newDays));
        assertEquals(newDays, course.getDays());

        course.setDefaultCategories();
        // assignments added to categories only, not to top‐level CourseAssignments
        assertFalse(course.getAssignments().iterator().hasNext());
    }

    @Test
    void testPanels() {
        JPanel panel = course.getAssignmentAddPanel();
        assertNotNull(panel);
        JTabbedPane tabs = course.getCourseView(true);
        assertEquals(3, tabs.getTabCount());
    }
}
