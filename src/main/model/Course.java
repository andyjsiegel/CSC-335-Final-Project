package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;

public class Course {
    private String code;
    private String name;
    private String credits;
    private String description;
    private Instructor instructor;
    private ArrayList<Days> daysOfWeek;
    private ArrayList<Student> studentList;

    private CourseAssignments assignments;
    private HashMap<String, Category> categories;

    
    public Course(String name, String code, String credits, String description,
                  Instructor instructor, ArrayList<Days> daysOfWeek) {
        
        this.name = name;
        this.code = code;
        this.credits = credits;
        
    	this.description = description;
        this.instructor = instructor;
        this.daysOfWeek = new ArrayList<Days>();
        this.daysOfWeek = daysOfWeek;
    	this.studentList = new ArrayList<Student>();
    	this.categories = new HashMap<String, Category>();
        this.assignments = new CourseAssignments();
    }

    //NOT USED ATM
    public Course(Course other) {
        this.name = other.name;
        this.code = other.code;
        this.description = other.description;
        this.instructor = new Instructor(other.instructor);
        this.daysOfWeek = new ArrayList<Days>(other.daysOfWeek);
        //this.studentList = new StudentList(other.studentList);
        //this.assignments = new CourseAssignments(other.assignments);
    }

    public CourseAssignments getAssignments() {
        return assignments;
    }
    
    public ArrayList<Student> getStudents() {
        return studentList;
    }

    public void addStudent(Student student) {
    	this.studentList.add(student);
    }
    
    public void removeStudent(Student student) {
    	this.studentList.remove(student);
    }
    
    public void addAssignment(Assignment assignment) {
    	this.assignments.addAssignment(assignment);
    }
    
    public void removeAssignment(Assignment assignment) {
    	this.assignments.removeAssignment(assignment);
    }
    
    public void addDays(ArrayList<Days> daysOfWeek) {
    	this.daysOfWeek = daysOfWeek;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCourseCode() {
    	return this.code;
    }
    public String getDescription() {
        return description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public ArrayList<Days> getDays() {
        return daysOfWeek;
    }
    
    public JTabbedPane getCourseView() {
        // Main panel with course details
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        JLabel nameLabel = new JLabel(name);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nameLabel, gbc);
        panel.setBackground(Color.GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setOpaque(false);
    
        JLabel emptyLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emptyLabel, gbc);
    
        JLabel instructorLabel = new JLabel("Instructors:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(instructorLabel, gbc);
    
        //for (Instructor instructor : instructors) {
            JLabel instructorName = new JLabel(instructor.getUsername());
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(instructorName, gbc);
        //}
    
        JLabel categoryLabel = new JLabel("Category Weights:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(categoryLabel, gbc);
    
        int gridYIterator = 4;
        for (String category : categories.keySet()) {
            JLabel categoryName = new JLabel(category);
            gbc.gridx = 0;
            gbc.gridy = gridYIterator;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(categoryName, gbc);
    
            JLabel categoryWeight = new JLabel(String.valueOf(categories.get(category).getPoints()) + " pts");
            gbc.gridx = 1;
            gbc.gridy = gridYIterator;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(categoryWeight, gbc);
            gridYIterator++;
        }
    
        // Create tabbed pane and add course detail panel as one tab
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Course Info", panel);
    
        JPanel assignmentsPanel = new JPanel();
        assignmentsPanel.setLayout(new BoxLayout(assignmentsPanel, BoxLayout.Y_AXIS));
        ArrayList<String> categorieTitles = new ArrayList<String>(categories.keySet());
        Collections.sort(categorieTitles);
        for (String category : categorieTitles) {
            ArrayList<Assignment> assignments = categories.get(category).getAssignments();

            // Header button for collapsible section
            JToggleButton toggleButton = new JToggleButton(category + " [" + categories.get(category).getPoints() + " pts]");
            toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Panel to hold assignments for this category
            JPanel assignmentsListPanel = new JPanel();
            JLabel assignmentsGraded = new JLabel();
            assignmentsGraded.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 2));
            assignmentsListPanel.setLayout(new BoxLayout(assignmentsListPanel, BoxLayout.Y_AXIS));
            assignmentsListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            assignmentsListPanel.add(assignmentsGraded);

            // Add each assignment as a label or more complex component
            int numGraded = 0;
            int pointsEarned = 0;
            int pointsPossible = categories.get(category).getPoints();
            for (Assignment assignment : assignments) {
                //assignment.setGradeTo100();
                if(assignment.getGrade() >= 0) {
                    pointsEarned += assignment.getGrade();
                    numGraded++;
                }
                JLabel assignmentLabel = new JLabel(assignment.getTitle() + " : " + assignment.getGrade());
                assignmentLabel.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 2));
                assignmentsListPanel.add(assignmentLabel);
            }
            
            assignmentsGraded.setText(numGraded + "/" + assignments.size() + " " + category + " graded: " + pointsEarned + "/" + pointsPossible + " points earned");
            // Initially collapsed
            assignmentsListPanel.setVisible(false);

            // Toggle visibility on button click
            toggleButton.addActionListener(e -> {
                assignmentsListPanel.setVisible(toggleButton.isSelected());
                // Revalidate and repaint to update UI
                assignmentsPanel.revalidate();
                assignmentsPanel.repaint();
            });

            // Add header and assignments list to main assignments panel
            assignmentsPanel.add(toggleButton);
            assignmentsPanel.add(assignmentsListPanel);
        }

        // Wrap assignmentsPanel in a JScrollPane in case of many assignments/categories
        JScrollPane scrollPane = new JScrollPane(assignmentsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tabbedPane.addTab("Assignments", scrollPane);

        return tabbedPane;
    }
}