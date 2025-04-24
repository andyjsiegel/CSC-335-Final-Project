package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// CategoryTest
class CategoryTest {
    Category cat;

    @BeforeEach
    void setup() {
        cat = new Category("Homeworks", 100, 25);
    }

    @Test
    void testAddDefaultAssignments() {
        cat.addDefaultAssignments();
        assertEquals(4, cat.getAssignments().size());
        // check first title contains singular form
        assertTrue(cat.getAssignments().get(0).getTitle().toLowerCase().contains("homework"));
    }

    @Test
    void testGettersSetters() {
        assertEquals(25, cat.getExpectedPoints());
        assertEquals(100, cat.getPoints());
        cat.setPoints(200);
        assertEquals(200, cat.getPoints());
        assertEquals("Homeworks", cat.getName());
        cat.setName("Exams");
        assertEquals("Exams", cat.getName());
    }

    @Test
    void testAddAssignment() {
        Assignment a = new Assignment("T","D",10);
        cat.addAssignment(a);
        assertEquals(1, cat.getAssignments().size());
        assertSame(a, cat.getAssignments().get(0));
    }
}
