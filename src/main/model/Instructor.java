package main.model;

import java.util.ArrayList;

public class Instructor extends User {
    private ArrayList<Course> coursesManaged;
    public Instructor(String username, String password, String email, String firstName, String lastName) {
        super(username, password, email, firstName, lastName);
    }

    public Instructor(String username, String password, String email, String name) {
        super(username, password, email, name);
    }
}