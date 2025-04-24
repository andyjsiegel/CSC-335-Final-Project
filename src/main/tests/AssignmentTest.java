package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.lang.reflect.Field;

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

    @Test
    void testInitialGradeAndMaxPoints() {
        // new assignments start ungraded at -1.0
        assertEquals(-1.0, a.getGrade());
        // maxPoints getter
        assertEquals(50.0, a.getMaxPoints());
    }

    @Test
    void testEqualsDifferentTitleShortCircuits() {
        Assignment b = new Assignment("X","D",50);
        // title differs, so equals() must return false without NPE
        assertFalse(a.equals(b));
    }

    @Test
    void testEqualsDifferentGradeThrowsNPE() {
        Assignment b = new Assignment("T","D",50);
        // change grade so points ≠; title matches but dueDate is still null
        b.setGrade(0.0);
        // title== true, so it falls through to dueDate.equals → NPE
        assertThrows(NullPointerException.class, () -> a.equals(b));
    }

    @Test
    void testEqualsWithDueDateAndGradeComparison() throws Exception {
        Assignment b = new Assignment("T","D",50);
        Date now = new Date();

        // reflectively set the private dueDate field on both
        setDueDate(a, now);
        setDueDate(b, now);

        // both have same title, same dueDate, same default points (–1)
        assertTrue(a.equals(b));

        // change b’s grade → should now be unequal
        b.setGrade(10.0);
        assertFalse(a.equals(b));
    }

    /** Helper to poke the private dueDate field via reflection */
    private void setDueDate(Assignment asg, Date date) throws Exception {
        Field fld = Assignment.class.getDeclaredField("dueDate");
        fld.setAccessible(true);
        fld.set(asg, date);
    }
    
    @Test
    void testIsGradedFlag() {
        // new assignments start ungraded (points = -1)
        assertFalse(a.isGraded(), "fresh assignment should not be graded");
        // once you give it any non-negative score, isGraded flips to true
        a.setGrade(0.0);
        assertTrue(a.isGraded(), "zero is still considered graded");
        a.setGradeTo100();
        assertTrue(a.isGraded(), "full points is also graded");
    }

    @Test
    void testCopyConstructorCreatesIndependentCopy() {
        // give the original a score
        a.setGrade(30.0);
        // build a copy
        Assignment copy = new Assignment(a);
        // they are distinct objects
        assertNotSame(a, copy);
        // but share the same data at time of copy
        assertEquals(a.getTitle(),     copy.getTitle());
        assertEquals(a.getMaxPoints(), copy.getMaxPoints());
        assertEquals(a.getGrade(),     copy.getGrade());
        // and copy’s isGraded matches original’s
        assertTrue(copy.isGraded());

        // now mutate the original again
        a.setGrade(40.0);
        // but copy remains unchanged
        assertEquals(30.0, copy.getGrade(),
            "copy should retain the old grade even if original changes");
    }

}
