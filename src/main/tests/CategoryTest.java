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
    
    @Test
    void testAddDefaultAssignmentsPlural() {
        cat.addDefaultAssignments();
        List<Assignment> list = cat.getAssignments();
        assertEquals(4, list.size(), "100/25 should yield 4 homeworks");

        // defaultName should be "Homework" (trim the 's')
        assertTrue(
            list.get(0).getTitle().toLowerCase().contains("homework"),
            "plural name should be trimmed to singular"
        );
        assertEquals(
            25.0,
            list.get(0).getMaxPoints(),
            "each default assignment must use expPointsPer"
        );
    }

    @Test
    void testAddDefaultAssignmentsSingularNameUnaffected() {
        // name doesn't end in 's', but 150/150 = 1 assignment
        Category single = new Category("Final Exam", 150, 150);
        single.addDefaultAssignments();
        List<Assignment> list = single.getAssignments();
        assertEquals(1, list.size());
        assertEquals("Final Exam", list.get(0).getTitle(),
            "singular category yields exactly one assignment with full name");
        assertEquals(150.0, list.get(0).getMaxPoints());
    }

    @Test
    void testAddDefaultAssignmentsMultipleNonPluralName() {
        // name "Project" (no trailing 's'), 100/25 = 4 assignments
        Category projs = new Category("Project", 100, 25);
        projs.addDefaultAssignments();
        List<Assignment> list = projs.getAssignments();
        assertEquals(4, list.size(), "100/25 should yield 4 projects");

        for (int i = 0; i < 4; i++) {
            Assignment a = list.get(i);
            assertEquals(
                "Project " + (i + 1),
                a.getTitle(),
                "title should be 'Project 1', 'Project 2', …"
            );
            assertEquals(
                25.0,
                a.getMaxPoints(),
                "all default assignments must use expPointsPer"
            );
        }
    }

    @Test
    void testGettersSettersAndAddManual() {
        // existing tests
        assertEquals(25, cat.getExpectedPoints());
        assertEquals(100, cat.getPoints());

        cat.setPoints(200);
        assertEquals(200, cat.getPoints());

        assertEquals("Homeworks", cat.getName());
        cat.setName("Exams");
        assertEquals("Exams", cat.getName());

        // manual add
        assertTrue(cat.getAssignments().isEmpty());
        Assignment manual = new Assignment("T", "D", 10);
        cat.addAssignment(manual);
        assertEquals(1, cat.getAssignments().size());
        assertSame(manual, cat.getAssignments().get(0));
    }
}
