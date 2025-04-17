package main.view;

import javax.swing.*;

import main.model.Course;
import main.model.Days;
import main.model.Instructor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InstructorView extends JFrame {

    public InstructorView(Instructor instructor) {
        // Set the title and size of the window
        setTitle("Instructor View");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the top section
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        userPanel.setBackground(Color.RED);

        // Add the instructor's name to the top left
        JLabel nameLabel = new JLabel("Instructor: " + instructor.getUsername());
        nameLabel.setForeground(Color.WHITE);
        userPanel.add(nameLabel, BorderLayout.WEST);

        // Add a log out button to the top right
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        userPanel.add(logoutButton, BorderLayout.EAST);
        add(userPanel, BorderLayout.NORTH);

        JPanel settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        settingsPanel.setBackground(Color.RED);

        JButton addClassButton = new JButton("Add Class");
        addClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace the current menu with the "Add Class" window
                getContentPane().removeAll();
                getContentPane().add(new AddClassView(), BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        settingsPanel.add(addClassButton, BorderLayout.EAST);
        add(settingsPanel, BorderLayout.SOUTH);

        // Create a panel for the center section
        JPanel centerPanel = new JPanel(new GridLayout(1, 7)); // 7 columns for days of the week
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Days of the week using the Days enum
        for (Days day : Days.values()) {
            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
            dayPanel.setBorder(BorderFactory.createTitledBorder(day.name()));

            // TODO: Have a calander like view to see all upcoming classes, reuqires an iterator for Courses in Instructor
            /*
            for (Course course : instructor.getCoursesForDay(day)) {
            JLabel classLabel = new JLabel(course.getName()); // Assuming Course has a getName() method
            classLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            dayPanel.add(classLabel);
            }
            */

            centerPanel.add(dayPanel);
        }

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Dummy AddClassView class for demonstration
    private class AddClassView extends JPanel {
        public AddClassView() {
            setLayout(new BorderLayout());
            JLabel label = new JLabel("Add Class Window");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            add(label, BorderLayout.CENTER);
        }
    }
}
