package main.view;

import main.controller.UserViewController;
import main.model.Course;
import main.model.Student;
import main.model.StudentList;

import javax.swing.*;
import java.awt.*;

public class ClassDetailsView extends JFrame {

    public ClassDetailsView(Course course, UserViewController controller) {
        setTitle("Class Details - " + course.getName());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Course Info Section
        JPanel topPanel = new JPanel(new GridLayout(4, 1));
        topPanel.add(new JLabel("Name: " + course.getName()));
        topPanel.add(new JLabel("Code: " + course.getCourseCode()));
        topPanel.add(new JLabel("Instructor: " + course.getInstructor().getFullName()));
        //topPanel.add(new JLabel("Days: " + String.join(", ", course.getDaysOfWeek())));

        add(topPanel, BorderLayout.NORTH);

        // Students (you'll wire this up to the controller)
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));

        StudentList students = course.getStudents(); // assuming this exists

        if (students == null || students.isEmpty()) {
            studentPanel.add(new JLabel("No students enrolled."));
        } else {
            for (Student student : students) {
                studentPanel.add(new JLabel(student.getFullName()));
            }
        }

        JScrollPane scrollPane = new JScrollPane(studentPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Enrolled Students"));
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
