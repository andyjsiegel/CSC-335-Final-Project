package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

// DaysTest
class DaysTest {
    @Test
    void testEnumContents() {
        assertEquals(5, Days.values().length);
        assertTrue(EnumSet.allOf(Days.class).contains(Days.MONDAY));
    }
}
