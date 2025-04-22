package main.view;

import main.controller.InstructorViewController;
import main.model.Course;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListViewClassesPanel extends JPanel {
    private InstructorViewController controller;

    public ListViewClassesPanel(InstructorViewController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JPanel courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));

        ArrayList<Course> courses = controller.getInstructorCourses();

        if (courses.isEmpty()) {
            courseListPanel.add(new JLabel("You are not teaching any classes."));
        } else {
            for (Course course : courses) {
                JPanel card = createCourseCard(course);
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
                courseListPanel.add(card);
                courseListPanel.add(Box.createRigidArea(new Dimension(0, 10))); // spacing
            }
        }

        JScrollPane scrollPane = new JScrollPane(courseListPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createCourseCard(Course course) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // visual feedback
    
        JLabel nameLabel = new JLabel("<html><b>" + course.getName() + "</b> (" + course.getCode() + ")</html>");
        JLabel instructorLabel = new JLabel("Instructor: " + course.getInstructor().getFullName());
    
        JTextArea descriptionArea = new JTextArea(course.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(panel.getBackground());
    
        JPanel top = new JPanel(new GridLayout(3, 1));
        top.add(nameLabel);
        top.add(instructorLabel);
    
        panel.add(top, BorderLayout.NORTH);
        panel.add(descriptionArea, BorderLayout.CENTER);
    
        // 🎯 Add Mouse Listener to open detail view
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new ClassDetailsView(course, controller); // open new window
            }
        });
    
        return panel;
    }
}
