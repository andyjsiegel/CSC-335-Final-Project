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
import java.io.FileNotFoundException;
import main.model.User;

public class UserDatabase {
    private String filePath;
    private HashMap<String, User> users = new HashMap<>();
    
    // provide a constructor with no parameters that uses default file path
    public UserDatabase() {
        this("src/main/controller/users.csv");
    }

    // provide a second  constructor that takes a file path as a parameter for testing
    public UserDatabase(String filePath) {
        this.filePath = filePath;
    }

    public void addUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.getUsername() + "," + user.getHashedPassword() + "\n");
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

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(","); // username,hashedPassword,email,name,role format

                // Ensure the userInfo array has the expected number of fields
                if (userInfo.length >= 5) {
                    String username = userInfo[0];
                    String hashedPassword = userInfo[1];
                    String email = userInfo[2];
                    String name = userInfo[3];
                    String role = userInfo[4];

                    // Create a User object based on the role
                    User user;
                    if (role.equalsIgnoreCase("Student")) {
                        user = new Student(username, hashedPassword, email, name);
                    } else if (role.equalsIgnoreCase("Instructor")) {
                        user = new Instructor(username, hashedPassword, email, name);
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
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    
    public HashMap<String, User> getUsers() {
        // make a copy of the map to avoid escaping reference - user is immutable so no need for deep copy
        return new HashMap<String, User>(users);  
    }

    public User getUser(String username, String password) {
        User user = users.getOrDefault(username, null);
        if(user == null) {
            return null; // cannot just return user because we need to do password validation
        }
        String hashedPassword = user.getHashedPassword();
        String hashPassword = User.hashPassword(password);
        if(hashPassword.equals(hashedPassword)) {
            return user; // return the user if the password is correct, then we can get the user's library.
        } else {
            return null;
        }
    }
}
