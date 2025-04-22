package main.view;

import main.controller.CourseController;
import main.model.Course;

import javax.swing.*;
import java.awt.*;
import java.util.List;
//TODO: This doesnt work
public class CourseView extends JPanel {
    private CourseController courseController;
    private JList<Course> courseList;
    private DefaultListModel<Course> courseListModel;
    private JTextArea courseDetailsArea;

    public CourseView(CourseController courseController) {
        this.courseController = courseController;
        initializeComponents();
        layoutComponents();
        registerListeners();
        updateCourseList();
    }

    private void initializeComponents() {
        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        courseDetailsArea = new JTextArea(10, 30);
        courseDetailsArea.setEditable(false);
        JButton createCourseButton = new JButton("Create New Course");
        JButton addStudentButton = new JButton("Add Student to Course");
        JButton addAssignmentButton = new JButton("Add Assignment to Course");

        add(createCourseButton);
        add(addStudentButton);
        add(addAssignmentButton);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(new JScrollPane(courseList), BorderLayout.WEST);
        add(courseDetailsArea, BorderLayout.CENTER);
    }

    private void registerListeners() {
        courseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Course selectedCourse = courseList.getSelectedValue();
                if (selectedCourse != null) {
                    displayCourseDetails(selectedCourse);
                }
            }
        });
    }

    private void displayCourseDetails(Course course) {
        StringBuilder details = new StringBuilder();
        details.append("Course Name: ").append(course.getName()).append("\n")
               .append("Course Code: ").append(course.getCourseCode()).append("\n")
               .append("Description: ").append(course.getDescription()).append("\n")
               .append("Students Enrolled: ").append(course.getStudents().size()).append("\n");
        courseDetailsArea.setText(details.toString());
    }

    public void updateCourseList() {
        courseListModel.clear();
        List<Course> courses = courseController.getCourses();
        for (Course course : courses) {
            courseListModel.addElement(course);
        }
    }
}