package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// StudentListTest
class StudentListTest {
    StudentList list;
    Student s1, s2;

    @BeforeEach
    void setup() {
        list = new StudentList();
        s1 = new Student("a","p","Alice","A","e",true);
        s2 = new Student("b","p","Bob","B","e",true);
    }

    @Test
    void testAddRemoveGet() {
        assertTrue(list.isEmpty());
        list.add(s1);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertSame(s1, list.get(0));
        list.remove(s1);
        assertTrue(list.isEmpty());
    }

    @Test
    void testSortAndMerge() {
        list.add(s2);
        list.add(s1);
        list.sortByFirstName();
        assertEquals(s1, list.get(0));
        StudentList other = new StudentList();
        other.add(s1);
        list.merge(other);
        // s1 and s2 remain, duplicate removed
        assertEquals(2, list.size());
    }
}
