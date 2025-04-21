package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Instructor extends User {
    private ArrayList<Course> coursesManaged;
    private String courseTest;
    
    public Instructor(String username, String password, String name, boolean isHashed) {
        super(username, password, name, isHashed);
        this.coursesManaged = new ArrayList<Course>();
    }

    public void setCourse(String courseTest) {
    	this.courseTest = courseTest;
    }
    

     public void addDetailsClass() {
        if (coursesManaged.isEmpty()) return;
    	    
        Course csc335 = coursesManaged.get(coursesManaged.size() - 1); // Get most recently added
    	    
         HashMap<String, Category> categoryWeights = new HashMap<>();
        //TODO: remove this default course
        categoryWeights.put("Short Assignments", new Category("Short Assignments", 125));  
        categoryWeights.put("Large Assignments", new Category("Large Assignments", 100));
        categoryWeights.put("Quizzes", new Category("Quizzes", 125));
        categoryWeights.put("Final Project", new Category("Final Project", 150));
        categoryWeights.put("Midterms", new Category("Midterms", 300));
        categoryWeights.put("Final Exam", new Category("Final Exam", 200));

        this.coursesManaged = new ArrayList<Course>();
        //this.coursesManaged = new ArrayList<Course>();
//        Course csc335 = new Course(
//        		coursesManaged.get(0).getName(), 
//            new ArrayList<Days>(Arrays.asList(Days.MONDAY, Days.TUESDAY, Days.WEDNESDAY, Days.THURSDAY, Days.FRIDAY)), 
//            categoryWeights);
//        
        csc335.setCategoryWeights(categoryWeights);
        //TODO: remove the default assignments
        // Adding Quizzes 1-7
        for (int i = 1; i <= 7; i++) {
            csc335.addAssignment(new Assignment("Quiz " + i, "Quizzes", 20));
        }

        // Adding Short Assignments 1-8
        for (int i = 1; i <= 8; i++) {
            csc335.addAssignment(new Assignment("Short Assignment " + i, "Short Assignments", 18));
        }

        // Adding Large Assignments 1 & 2
        csc335.addAssignment(new Assignment("Large Assignment 1", "Large Assignments", 50));
        csc335.addAssignment(new Assignment("Large Assignment 2", "Large Assignments", 50));

        // Adding both Midterms
        csc335.addAssignment(new Assignment("Midterm 1", "Midterms", 150));
        csc335.addAssignment(new Assignment("Midterm 2", "Midterms", 150));

        // Adding Final Project
        csc335.addAssignment(new Assignment("Final Project", "Final Project", 150));

        // Adding Final Exam
        csc335.addAssignment(new Assignment("Final Exam", "Final Exam", 200));
        addCourse(csc335);
    }

    
    public void addCourse(Course course) {
        this.coursesManaged.add(course);
        course.addInstructor(this);
    }

    
    public String getName() {
    	return this.username;
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