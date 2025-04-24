package main.tests;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;

import main.model.*;

import static org.junit.jupiter.api.Assertions.*;

// UserDatabaseTest
class UserDatabaseTest {
    Path tempFile;
    UserDatabase db;

    @BeforeEach
    void setup() throws IOException {
        tempFile = Files.createTempFile("users", ".csv");
        db = new UserDatabase(tempFile.toString());
    }

    @AfterEach
    void teardown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testAddStudentAndGetUser() {
        db.addStudent("stu","pass","F","L","e@mail", false);
        User u = db.getUser("stu","pass");
        assertNotNull(u);
        assertTrue(u instanceof Student);
        assertNull(db.getUser("stu","wrong"));
        assertNull(db.getUser("no","pass"));
    }

    @Test
    void testIterator() {
        db.addInstructor("ins","pwd","I","N","i@mail", false);
        int count = 0;
        for (User u : db) count++;
        assertEquals(1, count);
    }
}
