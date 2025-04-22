package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;

public class Course {
    private String code;
    private String name;
    private String description;
    private Instructor instructor;
    private ArrayList<Days> daysOfWeek;
    private ArrayList<Student> studentList;

    private CourseAssignments assignments;
    private HashMap<String, Category> categories;

    
    public Course(String name, String code, String description,
                  Instructor instructor, ArrayList<Days> daysOfWeek) {
        
        this.name = name;
        this.code = code;
    	this.description = description;
        this.instructor = instructor;
        this.daysOfWeek = new ArrayList<Days>();
    	this.studentList = new ArrayList<Student>();

        this.assignments = new CourseAssignments();
    }

    public Course(Course other) {
        this.name = other.name;
        this.code = other.code;
        this.description = other.description;
        this.instructor = new Instructor(other.instructor);
        this.daysOfWeek = new ArrayList<Days>(other.daysOfWeek);
        //this.studentList = new StudentList(other.studentList);
        //this.assignments = new CourseAssignments(other.assignments);
    }

    public CourseAssignments getAssignments() {
        return assignments;
    }
    
    public ArrayList<Student> getStudents() {
        return studentList;
    }

    public void addStudent(Student student) {
    	this.studentList.add(student);
    }
    
    public void removeStudent(Student student) {
    	this.studentList.remove(student);
    }
    
    public void addAssignment(Assignment assignment) {
    	this.assignments.addAssignment(assignment);
    }
    
    public void removeAssignment(Assignment assignment) {
    	this.assignments.removeAssignment(assignment);
    }
    
    public void addDays(ArrayList<Days> daysOfWeek) {
    	this.daysOfWeek = daysOfWeek;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public ArrayList<Days> getDaysOfWeek() {
        return daysOfWeek;
    }
}