package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// UserTest
class UserTest {
    @Test
    void testHashPasswordConsistency() {
        String h1 = User.hashPassword("password");
        String h2 = User.hashPassword("password");
        assertNotNull(h1);
        assertEquals(h1, h2);
        assertNotEquals("password", h1);
    }

    @Test
    void testAbstractGetters() {
        User dummy = new User("u","p","F","L","e",false) {
            @Override public ArrayList<Course> getCourses() { return new ArrayList<>(); }
            @Override public ArrayList<Course> getCoursesForDay(Days day) { return new ArrayList<>(); }
        };
        assertTrue(dummy.getCourses().isEmpty());
        assertTrue(dummy.getCoursesForDay(Days.FRIDAY).isEmpty());
    }
}
