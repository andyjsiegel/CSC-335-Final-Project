package main.util;

import main.model.Student;
import java.io.*;
import java.util.*;

public class CSVImporter {
    // Imports students from a CSV file.
    // Expected CSV format: username,password,name,role
    // Only students (role "Student") are imported.
    public static List<Student> importStudents(String filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split CSV line by commas.
                String[] tokens = line.split(",");
                if (tokens.length >= 4) {
                    String username = tokens[0].trim();
                    String password = tokens[1].trim();
                    String name = tokens[2].trim();
                    String role = tokens[3].trim();
                    // Only create a Student if the role matches.
                    if (role.equalsIgnoreCase("Student")) {
                        // The Student constructor expects a boolean for whether the password is hashed.
                        Student student = new Student(username, password, name, false);
                        students.add(student);
                    }
                }
            }
        }
        return students;
    }
}
