package main.tests;

import java.nio.file.*;

import main.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class UserDatabaseTest {
	
    Path tempFile;
    UserDatabase db;

    @TempDir 
    Path tempDir;
    private Path csvFile;

    @BeforeEach
    void setUp() {
        // We'll point our DB at a fresh users.csv in a temp folder
        csvFile = tempDir.resolve("users.csv");
    }

    @Test
    void testInitializeCreatesFileAndIsEmpty() {
        // File doesn't exist yet
        assertFalse(csvFile.toFile().exists());

        // Constructing with this path will call initializeDatabase()
        UserDatabase db = new UserDatabase(csvFile.toString());
        assertTrue(csvFile.toFile().exists(),
            "initializeDatabase() should create the CSV if it doesn't exist");
        
        // And since there's nothing in it, getUser must return null
        assertNull(db.getUser("anyone", "whatever"));
    }

    @Test
    void testAddStudentAndGetUserSuccess() {
        UserDatabase db = new UserDatabase(csvFile.toString());
        db.addStudent("alice", "pw123", "Alice", "Anderson", "alice@example.com", false);
        
        User u = db.getUser("alice", "pw123");
        assertNotNull(u, "Correct password should return the user");
        assertTrue(u instanceof Student, "alice should be a Student");
        assertEquals("alice", u.getUsername());
        assertEquals("Alice", u.getFirstName());
        assertEquals("Anderson", u.getLastName());
        assertEquals("alice@example.com", u.getEmail());
    }

    @Test
    void testGetUserWrongPasswordReturnsNull() {
        UserDatabase db = new UserDatabase(csvFile.toString());
        db.addStudent("bob", "secret", "Bob", "Brown", "bob@example.com", false);
        
        assertNull(db.getUser("bob", "wrongpass"),
            "Wrong password should yield null");
    }

    @Test
    void testGetUserUnknownUsernameReturnsNull() {
        UserDatabase db = new UserDatabase(csvFile.toString());
        // no users added yet
        assertNull(db.getUser("charlie", "anything"));
    }

    @Test
    void testAddInstructorAndIteration() {
        UserDatabase db = new UserDatabase(csvFile.toString());
        db.addStudent("sue", "studentpw", "Sue", "Smith", "sue@x.com", false);
        db.addInstructor("ian", "instructorpw", "Ian", "Ingram", "ian@x.com", false);
        
        // Collect all usernames via iterator()
        List<String> names = new ArrayList<>();
        for (User u : db) {
            names.add(u.getUsername());
        }
        // Order isn't guaranteed, so compare as sets
        assertEquals(
            List.of("sue", "ian").stream().sorted().collect(Collectors.toList()),
            names.stream().sorted().collect(Collectors.toList()),
            "Iterator should return both the student and instructor");
        }
    
    @Test
    void testInitializeDatabaseSkipsBadLinesAndDuplicates(@TempDir Path tempDir) throws Exception {
        // Prepare a CSV with:
        //   • too-short line (length < 6) → "Invalid user data"
        //   • unknown role         → "Unknown role"
        //   • a valid student
        //   • a duplicate of that student → "Username already exists"
        Path csv = tempDir.resolve("users.csv");
        List<String> lines = List.of(
            "too,short,line",                                       // < 6 fields
            "u1,hash1,fn1,ln1,email1",                              // exactly 5 fields
            "u2,hash2,fn2,ln2,email2,Admin",                        // role not Student/Instructor
            "good,hashG,First,Last,good@example.com,Student",       // valid
            "good,hashGdup,First,Last,good@example.com,Student"     // duplicate username
        );
        Files.write(csv, lines);

        // Constructing the DB will call initializeDatabase()
        UserDatabase db = new UserDatabase(csv.toString());       // :contentReference[oaicite:0]{index=0}&#8203;:contentReference[oaicite:1]{index=1}

        // Collect only the usernames that actually made it in
        List<String> names = new ArrayList<>();
        for (User u : db) {
            names.add(u.getUsername());
        }

        // Only "good" should have survived
        assertEquals(1, names.size(),  "Only the one valid Student should be added");
        assertEquals("good", names.get(0),  "Username 'good' should be present");
    }
    
    @Test
    void testSingletonDefaultConstructor() throws Exception {
        // Reset the singleton
        Field instField = UserDatabase.class.getDeclaredField("instance");
        instField.setAccessible(true);
        instField.set(null, null);

        // First call: should construct via private no-arg ctor
        UserDatabase first = UserDatabase.getInstance();
        assertNotNull(first, "getInstance() should return a non-null instance");

        // Second call: same singleton
        UserDatabase second = UserDatabase.getInstance();
        assertSame(first, second, "getInstance() must always return the same singleton");
    }
    
}
