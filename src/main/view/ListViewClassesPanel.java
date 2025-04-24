package main.view;

import main.controller.UserViewController;
import main.model.Course;
import main.model.Instructor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListViewClassesPanel extends JPanel {
    private UserViewController controller;

    public ListViewClassesPanel(UserViewController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JPanel courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));

        ArrayList<Course> courses = controller.getCourses();

        if (courses.isEmpty()) {
            String message = controller.getUser() instanceof Instructor ? "You are not teaching any classes." : "You are not enrolled in any classes.";
            courseListPanel.add(new JLabel(message));
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
    
        JLabel nameLabel = new JLabel("<html><b>" + course.getName() + "</b> (" + course.getCourseCode() + ")</html>");
        JLabel instructorLabel = new JLabel("Instructor: " + course.getInstructor().getFullName());
    
        JTextArea descriptionArea = new JTextArea(course.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFocusable(false);
        descriptionArea.setBackground(panel.getBackground());
    
        JPanel top = new JPanel(new GridLayout(3, 1));
        top.add(nameLabel);
        top.add(instructorLabel);
    
        panel.add(top, BorderLayout.NORTH);
        panel.add(descriptionArea, BorderLayout.CENTER);
    
        // now when a course is clicked on, it will show its course.
        // also im not sure why but it only works when you click on the BLACK TEXT
        // and not just anywhere on the screen, it was like that before so idek :skull:
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent evt) {
        		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        		if (topFrame instanceof MainView) {
        			MainView mainView = (MainView) topFrame;
        			mainView.showCourseDashboard(course);
        		}
        	}
        });
    
        return panel;
    }
}
