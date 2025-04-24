package main.view;


import main.controller.UserViewController;
import main.model.*;
import javax.swing.*;
import java.awt.*;

//TODO: this class needs to sue a controller, also needs to be split up into smaller classes
public class MainView extends JFrame {
    protected UserViewController controller;
    protected JPanel mainPanel;

    
    public MainView(User user) {
        this.controller = new UserViewController(user);
        setupWindow();
        setVisible(true);
    }

    
    private void setupWindow() {
        // Set up the main window
        setTitle("User Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a "File" menu
        JMenu fileMenu = new JMenu("File");

        // Create "Logout" menu item
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            dispose(); // Close the current window
            new LoginScreen(); // Open the login screen
        });

        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        // Create the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Create the bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 4));

        JButton addClassButton = new JButton("Add Class");
        addClassButton.addActionListener(e -> showAddClassPanel());

        JButton addAssignmentButton = new JButton("List View Classes");
        addAssignmentButton.addActionListener(e -> showListViewClassesPanel());

        JButton viewCalendarButton = new JButton("View Calendar");
        viewCalendarButton.addActionListener(e -> showCalendarPanel());

        //JButton testing = new JButton("testing");
        //testing.addActionListener(e -> showTestingPanel());
        
        if(controller.getUser() instanceof Instructor) bottomPanel.add(addClassButton);
        bottomPanel.add(addAssignmentButton);
        bottomPanel.add(viewCalendarButton);
        //bottomPanel.add(testing);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void showAddClassPanel() {
        mainPanel.removeAll();
        mainPanel.add(new AddClassPanel(controller.getUser(), controller), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showListViewClassesPanel() {
        mainPanel.removeAll();
    
        mainPanel.add(new ListViewClassesPanel(controller), BorderLayout.CENTER);
    
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showCourseDashboard(Course course) {
        mainPanel.removeAll();
        controller.setSelectedCourse(course);
        CourseDashboard dashboard = new CourseDashboard(e -> returnToMainPanel(), controller);
        mainPanel.add(dashboard, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

  
    private void returnToMainPanel() {
        mainPanel.removeAll();
     
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void showCalendarPanel() {
        new CalendarView(controller.getUser());
    }
}