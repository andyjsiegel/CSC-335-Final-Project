package main.controller;

import java.util.ArrayList;
import java.util.List;

import main.model.Course;
import main.model.Days;
import main.model.Gradebook;
import main.model.Instructor;

public class InstructorViewController {

    private Instructor instructor;
    private Gradebook gradebook = Gradebook.getInstance();

    public InstructorViewController(Instructor instructor) {
        this.instructor = instructor;
    }

    public void addCourse(String className, String classCode, String credits, String classDescription, List<String> selectedDays) {
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

        Course course = new Course(className, classCode, credits, classDescription, this.instructor, dayList);
        gradebook.addCourse(course);
        instructor.addCourse(course);
    }

    public ArrayList<Course> getInstructorCourses() {
        return instructor.getCoursesManaged();
    }
    
    public Instructor getInstructor() {
    	return this.instructor;
    }
}