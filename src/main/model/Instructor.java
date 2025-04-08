package main.model;

import java.util.ArrayList;

public class Instructor extends User {
    private ArrayList<Course> coursesManaged;

    public Instructor(String username, String password, String name, boolean isHashed) {
        super(username, password, name, isHashed);
    }
}