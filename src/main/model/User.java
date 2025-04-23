package main.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public abstract class User {
    protected String username;
    protected String password;
    protected String email;
    protected String firstName;
    protected String lastName;

    public User(String username, String password, String firstName, String lastName, String email, boolean isHashed) {
        this.username = username;
        if(isHashed) this.password = password;
        else this.password = hashPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static final byte[] salt = new byte[] { 
        (byte)0x1A, (byte)0x2B, (byte)0x3C, (byte)0x4D, 
        (byte)0x5E, (byte)0x6F, (byte)0x7A, (byte)0x8B, 
        (byte)0x9C, (byte)0xAD, (byte)0xBE, (byte)0xCF, 
        (byte)0xD0, (byte)0xE1, (byte)0xF2, (byte)0xFF 
    };

    // Public static method in order to use in other classes to validate log in.
    public static String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
    
    public abstract ArrayList<Course> getCourses();

    public abstract ArrayList<Course> getCoursesForDay(Days day);
}