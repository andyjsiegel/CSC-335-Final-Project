package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// StudentListTest
class StudentListTest {
    StudentList list;
    Student s1, s2, s3; 
    Course c; 

    @BeforeEach
    void setup() {
        list = new StudentList();
        s1 = new Student("a","p","Alice","A","e",true);
        s2 = new Student("b","p","Bob","B","e",true);
        s3 = new Student("c","p","Carol","C","e@mail", true);
        c = new Course("Course1","CS101","Desc",
            new Instructor("i","p","I","N","e@mail", false),
            new ArrayList<>(List.of(Days.MONDAY))
        );
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
    
    @Test
    void testCopyConstructorIndependence() {
        list.add(s1);
        list.add(s2);
        StudentList copy = new StudentList(list);
        assertEquals(2, copy.size());
        // remove from original, copy stays intact
        list.remove(s1);
        assertEquals(2, copy.size());
        assertEquals(1, list.size());
    }

    @Test
    void testIteratorCollectsAll() {
        list.add(s1);
        list.add(s2);
        List<Student> collected = new ArrayList<>();
        for (Student s : list) {
            collected.add(s);
        }
        assertEquals(Arrays.asList(s1, s2), collected);
    }

    @Test
    void testSortByLastNameAndUsername() {
        // Create students with swapped order
        Student alpha = new Student("x1","pw","Aaa","Zzz","e",false);
        Student beta  = new Student("x2","pw","Bbb","Yyy","e",false);
        list.add(beta);
        list.add(alpha);

        list.sortByLastName();
        // Zzz > Yyy, so beta (Yyy) comes first
        assertEquals(beta, list.get(0));

        list.sortByUsername();
        // x1 < x2
        assertEquals(alpha, list.get(0));
    }

    @Test
    void testEmptyClassMedianAndAverage() {
        Course dummy = new Course(
            "Dummy","DUM101","desc",
            new Instructor("i","p","I","N","e@mail", false),
            new ArrayList<>()
        );
        // no students or no assignments → both zero
        assertEquals(0.0, list.getClassMedian(dummy), 1e-9);
        assertEquals(0.0, list.getClassAverage(dummy), 1e-9);
    }

    @Test
    void testPartitionNormalAndException() {
        list.add(s1);
        list.add(s2);
        list.add(s1); // allow duplicate for grouping

        // group size 2 → two groups [s1,s2], [s1]
        List<StudentList> parts = list.partition(2);
        assertEquals(2, parts.size());
        assertEquals(2, parts.get(0).size());
        assertEquals(1, parts.get(1).size());

        // invalid group sizes
        assertThrows(IllegalArgumentException.class, () -> list.partition(0));
        assertThrows(IllegalArgumentException.class, () -> list.partition(-5));
    }
    
    @Test
    void testSortByAssignmentGradeWithMissingAndPresent() {
        // give only s1 and s2 a grade on "Test"
        s1.addCourse(c);
        s2.addCourse(c);
        s3.addCourse(c);

        StudentGradebook gb1 = s1.getGradebookForCourse(c);
        StudentGradebook gb2 = s2.getGradebookForCourse(c);
        // s1: 5/10 → 50%; s2: 10/10 →100%; s3: no assignment → -1.0
        gb1.addAssignment(new Assignment("Test","d",10), 5.0);
        gb2.addAssignment(new Assignment("Test","d",10),10.0);

        // build list in mixed order
        list.add(s2);
        list.add(s3);
        list.add(s1);

        list.sortByAssignmentGrade("Test");

        // missing (-1) comes first, then 50%, then 100%
        assertEquals(s3, list.get(0));
        assertEquals(s1, list.get(1));
        assertEquals(s2, list.get(2));
    }

    @Test
    void testGetClassMedianOddNumberOfGrades() {
        // only one student with one assignment
        s1.addCourse(c);
        StudentGradebook gb1 = s1.getGradebookForCourse(c);
        gb1.addAssignment(new Assignment("X","d",20), 10.0); // 50%

        list.add(s1);
        double median = list.getClassMedian(c);
        assertEquals(50.0, median, 1e-9, "Median of single element should be that element");
    }

    @Test
    void testGetClassMedianEvenNumberOfGrades() {
        // two students with one assignment each
        s1.addCourse(c);
        s2.addCourse(c);
        StudentGradebook gb1 = s1.getGradebookForCourse(c);
        StudentGradebook gb2 = s2.getGradebookForCourse(c);

        gb1.addAssignment(new Assignment("Y","d",10), 5.0);  // 50%
        gb2.addAssignment(new Assignment("Y","d",10),10.0);  //100%

        list.add(s1);
        list.add(s2);

        double median = list.getClassMedian(c);
        assertEquals((50.0 + 100.0) / 2.0, median, 1e-9,
            "Median of two elements should be their average");
    }

    @Test
    void testGetClassAverageNonZero() {
        // two students with one assignment each
        s1.addCourse(c);
        s2.addCourse(c);
        StudentGradebook gb1 = s1.getGradebookForCourse(c);
        StudentGradebook gb2 = s2.getGradebookForCourse(c);

        gb1.addAssignment(new Assignment("Z","d",20), 10.0); // 10/20
        gb2.addAssignment(new Assignment("Z","d",20), 20.0); // 20/20

        list.add(s1);
        list.add(s2);

        // total earned=30, total possible=40 → average=75%
        double avg = list.getClassAverage(c);
        assertEquals(75.0, avg, 1e-9);
    }
}
