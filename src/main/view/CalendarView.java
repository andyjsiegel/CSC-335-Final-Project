package main.view;

import javax.swing.*;

import main.model.Course;
import main.model.Days;
import main.model.Instructor;
import main.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;


public class CalendarView extends JFrame {


    public CalendarView(User user) {

        // Set the title and size of the window
        setTitle("Calendar View");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the top section
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        userPanel.setBackground(Color.GRAY);

        // Add the instructor's name to the top left
        JLabel nameLabel = new JLabel(user.getClass().getSimpleName() + ": " + user.getUsername());
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
        settingsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        settingsPanel.setBackground(Color.GRAY);

        JButton addClassButton = new JButton("Add Class");
        addClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                //getContentPane().add(new AddClassView(), BorderLayout.CENTER);
                revalidate();
                repaint();
                //new CalendarView((Instructor) user);
            }
        });
        settingsPanel.add(addClassButton, BorderLayout.EAST);
        add(settingsPanel, BorderLayout.SOUTH);

        // Create a panel for the center section
        JPanel centerPanel = new JPanel(new GridLayout(1, 7)); // 7 columns for days of the week
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        for (Days day : Days.values()) {
            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
            dayPanel.setBorder(BorderFactory.createTitledBorder(day.name()));
            
            // track displayed courses and avoid duplicates
            Set<String> displayedCourses = new HashSet<>();
            
            //System.out.println("Checking "  + " | Days=" + day + user.getCoursesForDay(day));
            
            for (Course course : user.getCoursesForDay(day)) {
                boolean isInstructor = user instanceof Instructor;
            	//System.out.println("Checking " + day + ": " + course.getName() + " | Days=" + course.getDays());

                // Only add if we haven't displayed this course yet
                if (!displayedCourses.contains(course.getCourseCode())) {
                    JButton classLabel = new JButton(course.getName());
                    classLabel.setText("<html><center>"+course.getName()+"<br>("+course.getCourseCode()+")</center></html>");
                    classLabel.addActionListener(_ -> {
                    	 JOptionPane.showMessageDialog(null, course.getCourseView(isInstructor), course.getCourseCode(), JOptionPane.PLAIN_MESSAGE);
                    });
                    
                    dayPanel.add(classLabel);
                    displayedCourses.add(course.getCourseCode());
                }
            }
            
            centerPanel.add(dayPanel);
        }

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}