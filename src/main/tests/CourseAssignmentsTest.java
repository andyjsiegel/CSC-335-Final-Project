package main.tests;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import main.model.*;

import static org.junit.jupiter.api.Assertions.*;

// CourseAssignmentsTest
class CourseAssignmentsTest {
    CourseAssignments assignments;
    Assignment a1, a2;

    @BeforeEach
    void setup() {
        assignments = new CourseAssignments();
        a1 = new Assignment("T1","D1",10);
        a2 = new Assignment("T2","D2",20);
    }

    @Test
    void testAddSizeIterator() {
        assertEquals(0, assignments.size());
        assignments.add(a1);
        assignments.add(a2);
        assertEquals(2, assignments.size());
        Iterator<Assignment> it = assignments.iterator();
        assertTrue(it.hasNext());
        assertSame(a1, it.next());
        assertSame(a2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testRemove() {
        assignments.add(a1);
        assignments.add(a2);
        assignments.remove(a1);
        assertEquals(1, assignments.size());
        assertSame(a2, assignments.iterator().next());
    }
}
