package main.model;

import java.util.ArrayList;

public class Instructor extends User {
    private ArrayList<Course> coursesManaged;

    //String username, String password, String firstName, String lastName, String email, boolean isHashed
    public Instructor(String username, String password, String firstname, String lastname, String email, boolean isHashed) {
        super(username, password, firstname, lastname, email, isHashed);
        coursesManaged = new ArrayList<Course>();
    }

    public Instructor(Instructor other) {
        super(other.username, other.password, other.firstname, other.lastname, other.email, true);
        this.coursesManaged = new ArrayList<Course>(other.coursesManaged);
    }
    
    public void addCourse(Course course) {
        this.coursesManaged.add(course);
    }

    public ArrayList<Course> getCoursesManaged() {
        ArrayList<Course> copy = new ArrayList<Course>();
        for (Course course : this.coursesManaged) {
            copy.add(new Course(course));
        }
        return copy;
    }

    public ArrayList<String> getFullName() {
        ArrayList<String> name = new ArrayList<String>();
    	name.add(this.firstname);
        name.add(this.lastname);
        return name;
    }

    public String toString() {
        return "Instructor " + this.firstname + " " + this.lastname + " with username " + username;
    }
}