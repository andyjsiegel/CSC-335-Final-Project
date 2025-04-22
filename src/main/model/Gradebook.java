package main.model;

import java.util.ArrayList;

public class Gradebook {
    private ArrayList<Instructor> instructors;
    private ArrayList<Student> students;
    private ArrayList<Course> courses;

    private static Gradebook instance;

    public static Gradebook getInstance() {
        if (instance == null) {
            instance = new Gradebook();
        }
        return instance;
    }

    public Gradebook() {
        instructors = new ArrayList<>();
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
