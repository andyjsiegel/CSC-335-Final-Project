package main.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

public class UserDatabase {
    private String filePath;
    private HashMap<String, User> users = new HashMap<>();
    
    // Default constructor with default file path
    public UserDatabase() {
        this("src/main/controller/users.csv");
    }

    // Constructor with custom file path
    public UserDatabase(String filePath) {
        this.filePath = filePath;
        initializeDatabase();
    }

    public void addStudent(String username, String password, String firstname, String lastname, String email, boolean isHashed) {
        User user = new Student(username, password, firstname, lastname, email, isHashed);
        addUser(user);
    }

    public void addInstructor(String username, String password, String firstname, String lastname, String email, boolean isHashed) {
        User user = new Instructor(username, password, firstname, lastname, email, isHashed);
        addUser(user);
    }

    public void addUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.getUsername() + "," + user.getHashedPassword() + "," + user.getFirstname() + "," + user.getLastname() + "," + user.getEmail() + "," + user.getClass().getSimpleName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        users.put(user.getUsername(), user);
    }

    private void initializeDatabase() {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(","); // username,hashedPassword,email,name,role format

                // Ensure the userInfo array has the expected number of fields
                if (userInfo.length >= 6) {
                    String username = userInfo[0];
                    String hashedPassword = userInfo[1];
                    String firstname = userInfo[2];
                    String lastname = userInfo[3];
                    String email = userInfo[4];
                    String role = userInfo[5];

                    // Create a User object based on the role
                    User user;
                    if (role.equalsIgnoreCase("Student")) {
                        user = new Student(username, hashedPassword, firstname, lastname, email, true);
                    } else if (role.equalsIgnoreCase("Instructor")) {
                        user = new Instructor(username, hashedPassword, firstname, lastname, email, true);
                    } else {
                        System.err.println("Unknown role: " + role);
                        continue; // Skip unknown roles
                    }

                    users.put(username, user);
                } else {
                    System.err.println("Invalid user data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, User> getUsers() {
        // Return a copy of the map to avoid escaping reference
        return new HashMap<>(users);
    }

    public User getUser(String username, String password) {
        User user = users.get(username);

        System.out.println("User: " + user);

        if (user == null) {
            return null; // User not found
        }

        //error here
        String hashedPassword = user.getHashedPassword();
        String hashPassword = User.hashPassword(password);

        if (hashPassword.equals(hashedPassword)) {
            return user;
        } else {
            return null;
        }
    }
}
