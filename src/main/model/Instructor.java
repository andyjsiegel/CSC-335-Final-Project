package main.model;

import java.util.ArrayList;

public class Instructor extends User {
    private ArrayList<Course> coursesManaged;

    //String username, String password, String firstName, String lastName, String email, boolean isHashed
    public Instructor(String username, String password, String firstName, String lastName, String email, boolean isHashed) {
        super(username, password, firstName, lastName, email, isHashed);
        coursesManaged = new ArrayList<Course>();
    }

    public Instructor(Instructor other) {
        super(other.username, other.password, other.firstName, other.lastName, other.email, true);
        this.coursesManaged = new ArrayList<Course>(other.coursesManaged);
    }
    
    public void addCourse(Course course) {
        this.coursesManaged.add(course);
    }

//    public ArrayList<Course> getCoursesManaged() {
//        ArrayList<Course> copy = new ArrayList<Course>();
//        for (Course course : this.coursesManaged) {
//            copy.add(new Course(course));
//        }
//        return copy;
//    }
    public ArrayList<Course> getCoursesManaged() {
        return this.coursesManaged;
    }


    public String getFullName() {
       return firstName + " " + lastName;
    }

    public String toString() {
        return "Instructor " + this.firstName + " " + this.lastName + " with username " + username;
    }

    public ArrayList<Course> getCoursesForDay(Days day) {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (Course course : this.coursesManaged) {
            if (course.getDays().contains(day)) {
                courses.add(course);
            }
        }
        return courses;
        
    }
}