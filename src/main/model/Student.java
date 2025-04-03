package main.model;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Course> coursesTaken;
    public Student(String username, String password, String email, String firstName, String lastName) {
        super(username, password, email, firstName, lastName);
    }

    public Student(String username, String password, String email, String name) {
        super(username, password, email, name);
    }
}