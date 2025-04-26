package main.tests;

import main.model.Assignment;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {
    private Assignment a;

    @BeforeEach
    void setup() {
        a = new Assignment("T","D",50);
    }

    @Test
    void testConstructorDefaults() {
        assertEquals("T", a.getTitle());
        assertEquals(50.0, a.getMaxPoints(), 1e-9);
        // ungraded: points=-1
        assertEquals(-1.0, a.getPointsEarned(), 1e-9);
        assertFalse(a.isGraded(), "fresh assignment should not be graded");
    }

    @Test
    void testDescriptionSetterReflectively() throws Exception {
        a.setAssignmentDescription("Desc2");
        Field f = Assignment.class.getDeclaredField("description");
        f.setAccessible(true);
        assertEquals("Desc2", f.get(a));
    }

    @Test
    void testGradeMethodsAndIsGraded() {
        a.setGradeTo100();
        assertEquals(50.0, a.getPointsEarned(), 1e-9);
        assertTrue(a.isGraded(), "setGradeTo100 should mark graded");

        a.setPointsEarned(25.0);
        assertEquals(25.0, a.getPointsEarned(), 1e-9);
        assertTrue(a.isGraded());
    }

    @Test
    void testGetGradeCalculations() {
        // still ungraded
        assertEquals(-1.0, a.getGrade(), 1e-9);
        a.setPointsEarned(25.0);
        assertEquals(0.5, a.getGrade(), 1e-9);
        a.setGradeTo100();
        assertEquals(1.0, a.getGrade(), 1e-9);
    }

    @Test
    void testCategoryGetterSetter() {
        assertNull(a.getCategory(), "default category is null");
        a.setCategory("HW");
        assertEquals("HW", a.getCategory());
    }

    @Test
    void testCopyConstructorIndependence() {
        a.setPointsEarned(30.0);
        a.setCategory("C1");
        Assignment copy = new Assignment(a);
        assertNotSame(a, copy);
        assertEquals(a.getTitle(), copy.getTitle());
        assertEquals(a.getMaxPoints(), copy.getMaxPoints(), 1e-9);
        assertEquals(a.getPointsEarned(), copy.getPointsEarned(), 1e-9);
        assertEquals("C1", copy.getCategory());

        // mutating original should not affect copy
        a.setPointsEarned(40.0);
        a.setCategory("C2");
        assertEquals(30.0, copy.getPointsEarned(), 1e-9);
        assertEquals("C1", copy.getCategory());
    }

    @Test
    void testEqualsReflexiveAndNullAndOtherClass() {
        assertTrue(a.equals(a));
        assertFalse(a.equals(null));
        assertFalse(a.equals(new Object()));
    }

    @Test
    void testEqualsDifferentTitleShortCircuits() {
        Assignment b = new Assignment("X","D",50);
        assertFalse(a.equals(b));
    }

    @Test
    void testEqualsDifferentGradeThrowsNPE() {
        Assignment b = new Assignment("T","D",50);
        b.setPointsEarned(0.0);
        assertThrows(NullPointerException.class, () -> a.equals(b));
    }

    @Test
    void testEqualsWithDueDateAndPointComparison() throws Exception {
        Assignment b = new Assignment("T","D",50);
        Date now = new Date();
        // set dueDate on both
        Field fd = Assignment.class.getDeclaredField("dueDate");
        fd.setAccessible(true);
        fd.set(a, now);
        fd.set(b, now);

        // same title, dueDate, default points
        assertTrue(a.equals(b));

        // different points → false
        b.setPointsEarned(10.0);
        assertFalse(a.equals(b));
    }

    @Test
    void testEqualsIgnoresCategory() throws Exception {
        Assignment b = new Assignment("T","D",50);
        Date now = new Date();
        Field fd = Assignment.class.getDeclaredField("dueDate");
        fd.setAccessible(true);
        fd.set(a, now);
        fd.set(b, now);
        a.setPointsEarned(20.0);
        b.setPointsEarned(20.0);
        a.setCategory("A");
        b.setCategory("B");
        assertTrue(a.equals(b) && b.equals(a));
    }
}
