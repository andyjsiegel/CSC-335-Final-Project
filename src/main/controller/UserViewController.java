package main.controller;

import java.util.ArrayList;
import java.util.List;

import main.model.Course;
import main.model.Days;
import main.model.Gradebook;
import main.model.User;
import main.model.Instructor;

public class UserViewController {

    private User user;
    private Gradebook gradebook = Gradebook.getInstance();

    public UserViewController(User user) {
        this.user = user;
    }

    public void addCourse(String className, String classCode, String credits, String classDescription, List<String> selectedDays) {
        if (!(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor and thus cannot create a course.");
        }
        ArrayList<Days> dayList = new ArrayList<>();
        for (String day : selectedDays) {
            if (day.equalsIgnoreCase("Mon")) {
                dayList.add(Days.MONDAY);
            } else if (day.equals("Tue")) {
                dayList.add(Days.TUESDAY);
            } else if (day.equals("Wed")) {
                dayList.add(Days.WEDNESDAY);
            } else if (day.equals("Thu")) {
                dayList.add(Days.THURSDAY);
            } else if (day.equals("Fri")) {
                dayList.add(Days.FRIDAY);
            }
        }

        Course course = new Course(className, classCode, credits, classDescription, (Instructor) this.user, dayList);
        course.setDefaultCategories();
        course.addAllStudentsFromPool();
        gradebook.addCourse(course);
        Instructor instructor = (Instructor) user;
        instructor.addCourse(course);
    }

    public ArrayList<Course> getCourses() {
        return user.getCourses();
    }
    
    public User getUser() {
    	return this.user;
    }
}