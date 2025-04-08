package main.model;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Course> coursesTaken;
    public Student(String username, String password, String name, boolean isHashed) {
        super(username, password, name, isHashed);
    }
}