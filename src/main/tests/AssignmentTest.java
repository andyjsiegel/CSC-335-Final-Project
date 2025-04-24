package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import static org.junit.jupiter.api.Assertions.*;

// AssignmentTest
class AssignmentTest {
    Assignment a;

    @BeforeEach
    void setup() {
        a = new Assignment("T","D",50);
    }

    @Test
    void testTitleAndDescription() {
        assertEquals("T", a.getTitle());
        a.setAssignmentDescription("DD");
    }

    @Test
    void testGradeMethods() {
        a.setGradeTo100();
        assertEquals(50, a.getGrade());
        a.setGrade(25.0);
        assertEquals(25.0, a.getGrade());
        assertEquals(50, a.getMaxPoints());
    }

    @Test
    void testEqualsBasics() {
        assertTrue(a.equals(a));
        assertFalse(a.equals(null));
        assertFalse(a.equals(new Object()));
    }
}
