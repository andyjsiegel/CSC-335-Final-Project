package main.model;

import java.util.ArrayList;

public class Instructor extends User {
    private ArrayList<Course> coursesManaged;

    public Instructor(String username, String password, String email, boolean isHashed) {
        super(username, password, email, isHashed);
    }

    public Course getCoursesForDay(Days day) {
        for (Course course : coursesManaged) {
            if (course.getDays().contains(day)) {
                return course;
            }
        }
        return null; // No class found for the given day
    }
}