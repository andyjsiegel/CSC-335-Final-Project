package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

// GradebookTest
class GradebookTest {
    @Test
    void testSingleton() {
        Gradebook g1 = Gradebook.getInstance();
        Gradebook g2 = Gradebook.getInstance();
        assertSame(g1, g2);
    }

    @Test
    void testAddMethodsNoException() {
        Gradebook gb = new Gradebook();
        gb.addInstructor(new Instructor("u","p","I","N","e",true));
        gb.addStudent(new Student("u","p","F","L","e",true));
        gb.addCourse(new Course("N","C","D",
            new Instructor("u","p","I","N","e",true),
            new ArrayList<>()));
    }
}
