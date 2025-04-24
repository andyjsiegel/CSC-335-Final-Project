package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

// StudentGroupTest
class StudentGroupTest {
    StudentGroup group;
    Student s;

    @BeforeEach
    void setup() {
        group = new StudentGroup();
        s = new Student("u","p","F","L","e",true);
    }

    @Test
    void testAddAndIterator() {
        group.addStudent(s);
        Iterator<Student> it = group.iterator();
        assertTrue(it.hasNext());
        assertSame(s, it.next());
        assertFalse(it.hasNext());
    }
}
