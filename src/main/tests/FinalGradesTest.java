package main.tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;

import main.model.FinalGrades;

class FinalGradesTest {
    @Test
    void testEnumContents() {
        assertEquals(5, FinalGrades.values().length);
        assertTrue(EnumSet.allOf(FinalGrades.class).contains(FinalGrades.A));
    }
}
