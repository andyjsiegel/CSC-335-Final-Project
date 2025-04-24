package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// GradingSchemeTest
class GradingSchemeTest {
    GradingScheme scheme;
    List<AssignmentGrade> grades;
    Map<String,Double> weights;
    Map<String,Integer> drops;

    @BeforeEach
    void setup() {
        weights = new HashMap<>();
        weights.put("HW", 0.5);
        weights.put("Exam", 0.5);
        drops = new HashMap<>();
        drops.put("HW", 1);
        scheme = new GradingScheme(weights, drops);

        grades = new ArrayList<>();
        grades.add(new AssignmentGrade(null,80,100,"HW"));
        grades.add(new AssignmentGrade(null,90,100,"HW"));
        grades.add(new AssignmentGrade(null,70,100,"Exam"));
    }

    @Test
    void testOption1() {
        double f = scheme.calculateFinalGradeOption1(grades);
        assertEquals((80+90+70)/(300.0), f);
    }

    @Test
    void testOption2() {
        // drop lowest HW, average HW = .9, Exam = .7 => .9*.5 + .7*.5 = .8
        assertEquals(0.8, scheme.calculateFinalGradeOption2(grades));
    }

    @Test
    void testGetters() {
        assertSame(weights, scheme.getCategoryWeights());
        assertSame(drops, scheme.getDropCounts());
    }
}
