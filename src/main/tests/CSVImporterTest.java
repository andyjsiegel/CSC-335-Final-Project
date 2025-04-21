package main.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.model.Student;
import main.util.CSVImporter;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class CSVImporterTest {
    @Test
    public void testImportStudents() throws IOException {
        // Create a temporary CSV file with sample student data.
        Path tempFile = Files.createTempFile("students", ".csv");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            // CSV format: username,password,name,role.
            writer.write("john_doe,password123,John Doe,Student\n");
            writer.write("jane_doe,password456,Jane Doe,Student\n");
            writer.write("prof_smith,password789,Prof. Smith,Instructor\n");
        }

        // Import students using the CSVImporter.
        List<Student> students = CSVImporter.importStudents(tempFile.toString());
        // Only two students should be imported (role "Student" only).
        assertEquals(2, students.size(), "CSVImporter should import 2 students");

        // Verify the first student's username.
        Student student1 = students.get(0);
        assertEquals("john_doe", student1.getUsername(), "First student's username should be 'john_doe'");

        // Clean up the temporary file.
        Files.deleteIfExists(tempFile);
    }
}

