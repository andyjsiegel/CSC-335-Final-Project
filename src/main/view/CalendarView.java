package main.view;

import main.model.Course;
import main.model.Instructor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//TODO:This doesnt work
public class CalendarView extends JFrame{
    private Instructor instructor;
    private JTextArea calendarArea;

    public CalendarView(Instructor instructor) {
        this.instructor = instructor;
        setTitle("Calendar View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        calendarArea = new JTextArea();
        calendarArea.setEditable(false);
        add(new JScrollPane(calendarArea), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Calendar");
        refreshButton.addActionListener(e -> updateCalendar());
        add(refreshButton, BorderLayout.SOUTH);

        updateCalendar();
    }

    private void updateCalendar() {
        StringBuilder calendarContent = new StringBuilder();
        List<Course> courses = instructor.getCoursesManaged(); // Assuming getCourses() method exists

        for (Course course : courses) {
            calendarContent.append("Course: ").append(course.getName()).append("\n");
            calendarContent.append("Assignments:\n");
            course.getAssignments().forEach(assignment -> 
                calendarContent.append(" - ").append(assignment.getName()).append("\n")
            );
            calendarContent.append("\n");
        }

        calendarArea.setText(calendarContent.toString());
    }
}