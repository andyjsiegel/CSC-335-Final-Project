package main.tests;

import main.model.*;            // :contentReference[oaicite:2]{index=2}&#8203;:contentReference[oaicite:3]{index=3}
import main.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CSVImporterTest {

    @TempDir
    Path tempDir;

    @Test
    void testImportStudentsFiltersByRoleAndTrimsWhitespace() throws IOException {
        // Prepare a CSV with mixed roles and varying case/whitespace
        List<String> lines = List.of(
            "  alice  ,  pwA  ,  Alice  ,  Adams  , alice@x.com  ,  Student  ",
            "bob,bobPw,Bob,Baker,bob@x.com,Instructor",
            "carol, pwC , Carol , Carter , carol@x.com , student",
            "dan,danPw,Dan,Donovan,dan@x.com,OTHER",
            "eve,evePw,Eve,Evans,eve@x.com,STUDENT"
        );

        // Write to temp file
        Path csv = tempDir.resolve("students.csv");
        Files.write(csv, lines);

        // Run importer
        List<Student> students = CSVImporter.importStudents(csv.toString());

        // We expect exactly alice, carol, and eve
        assertEquals(3, students.size(), "Only 3 Student entries should be imported");

        // Index by username for easy lookup
        Map<String, Student> byUsername = students.stream()
            .collect(Collectors.toMap(Student::getUsername, s -> s));

        // alice
        Student alice = byUsername.get("alice");
        assertNotNull(alice, "alice should be present");
        assertEquals("Alice",  alice.getFirstName());
        assertEquals("Adams",  alice.getLastName());
        assertEquals("alice@x.com", alice.getEmail());
        // Since importer sets isHashed=true, the password field is stored verbatim
        assertEquals("pwA", alice.getHashedPassword());

        // carol (lowercase “student”)
        Student carol = byUsername.get("carol");
        assertNotNull(carol, "carol should be present");
        assertEquals("Carol", carol.getFirstName());
        assertEquals("Carter", carol.getLastName());
        assertEquals("pwC",   carol.getHashedPassword());

        // eve (uppercase “STUDENT”)
        Student eve = byUsername.get("eve");
        assertNotNull(eve, "eve should be present");
        assertEquals("Eve",   eve.getFirstName());
        assertEquals("Evans", eve.getLastName());
        assertEquals("evePw", eve.getHashedPassword());
    }
}
