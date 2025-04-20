package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Course {
    private ArrayList<Student> students;
    private ArrayList<Instructor> instructors;
    private HashMap<String, Double> categoryWeights;
    private String name;
    private String description;
    private int credits;
    private LocalTime startTime;
    private LocalTime endTime;
    private ArrayList<Days> daysOfWeek;
    private String courseCode;
    
    
    
    private StudentList studentList;
    private CourseAssignments assignments;
    private HashMap<String, Category> categories;
    
    public Course(String name, String description, int credits, ArrayList<String> daysOfWeek, LocalTime startTime, LocalTime endTime, HashMap<String, Double> categoryWeights, String courseCode) {
        this.name = name;
        this.description = description;
        this.courseCode = courseCode;
        this.credits = credits;
     
        //this.students = new ArrayList<Student>();
        this.instructors = new ArrayList<Instructor>();
        this.categoryWeights = categoryWeights;
       // this.daysOfWeek = new ArrayList<String>();
        this.startTime = startTime;
        this.endTime = endTime;
       // this.daysOfWeek = daysOfWeek;
    }

    public CourseAssignments getAssignments() {
    	return assignments;
    }
    public String getDescription() {
    	return this.description;
    }
    
    public StudentList getStudents() {
    	return studentList;
    }
    
    public int getCredits() {
    	return credits;
    }
	/*
	 * A normal course, for instance, we would get 
	 * 
	 * CSC 335 - Object-Oriented Programming and Design
	 * 3.00 Units
	 * 
	 * Class Code
	 * 48423
	 * 
	 * [DESCRIPTION]
	 * 
	 */
    public Course(String name, String description, int credits, String courseCode) {

    	this.name = name;
    	this.description = description;
    	this.credits = credits;
    	this.courseCode = courseCode;
        this.instructors = new ArrayList<Instructor>();
    	this.studentList = new StudentList();		// each course has a student List
    	this.assignments = new CourseAssignments();
    }
    
    public void setCategoryWeights(HashMap<String, Category> categories) {
        this.categories = categories;
    }
    
    public String getCourseCode() {
    	return this.courseCode;
    }
    public void addStudent(Student student) {
    	this.studentList.addStudent(new Student(student));
    }
    
    
    public void removeStudent(Student student) {
    	this.studentList.removeStudent(student);
    }
    
    
    public void addAssignment(Assignment assignment) {
    	this.assignments.addAssignment(assignment);
    }
    
    
    public void removeAssignment(Assignment assignment) {
    	this.assignments.removeAssignment(assignment);
    }
    
    

	
    /* 
     * One Course, let's say the name of it is CSC 335
     * Things/ Methods that need to be added for the Course.java class:
     * 
     * 
     * 
     * Import a list of students to add to the course: Well i guess same idea, it involvs modyfing the StudentList class
     * View the students enrolled in a course: easy enough, just make a method in student list
     * 
     * Calculate class averages and medians: easy enough, a student has a student gradeBook, and that cotnains the 
     * 				average of the student, the total points, etc all neeeded for median/average.
     * sort students by grades on an assingment : easy enough again, we just have to add the students first and then do allat
     * 
     * 	View ungraded assignments.			(meaning an assingment must have a graded flag, (( if graded = true, cant modify grade? prob not))
	 	Choose a mode for calculating class averages. ( no idea what this even means ngl)
		Set up categories of assignments with weights, allowing for dropped assignments. (so basically modify the assignments class again
						this is a bit more complex i guess but it should still work relatively the same way ??
						-> so adding a catergory to an assignment, prob not worth to make a category class but worth looking into

     * 
     */
    
    public void addDays(ArrayList<Days> daysOfWeek) {
    	this.daysOfWeek = daysOfWeek;
    }
    


    
    
    

    public String getCode() {
    	return courseCode;
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
    
        for (Instructor instructor : instructors) {
            JLabel instructorName = new JLabel(instructor.getUsername());
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(instructorName, gbc);
        }
    
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
                if(assignment.getPointsEarned() != null) {
                    pointsEarned += assignment.getPointsEarned();
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        StringBuilder sb = new StringBuilder();
        sb.append("Course Code: ").append(courseCode).append("\n");
        sb.append("Course Name: ").append(name).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Credits: ").append(credits).append("\n");
        
        // Only show time if it's set
        if (startTime != null && endTime != null) {
            sb.append("Time: ").append(startTime.format(timeFormatter))
              .append(" - ").append(endTime.format(timeFormatter)).append("\n");
        }
        
        // Only show weights if they exist
        if (categoryWeights != null && !categoryWeights.isEmpty()) {
            sb.append("Category Weights:\n");
            for (String category : categoryWeights.keySet()) {
                sb.append("  ").append(category).append(": ")
                  .append(categoryWeights.get(category)).append("\n");
            }
        }

        return sb.toString();
    }

    public JPanel createEventPanel() {
        // Calculate duration in minutes
        long duration = Duration.between(startTime, endTime).toMinutes();
        // Set panel height based on duration
        int panelHeight = (int) duration * 2; // For example, 2 pixels per minute
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, panelHeight));
        panel.setBackground(Color.BLUE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add label for course name
        JLabel label = new JLabel(name);
        label.setForeground(Color.WHITE);
        panel.add(label);

        return panel;
    }
    public String getName() {
        return name;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public long getDuration() {
        return Duration.between(startTime, endTime).toMinutes();
  }
    public ArrayList<Days> getDays() {
        return daysOfWeek;
    }
    
    public void addInstructor(Instructor instructor) {
        this.instructors.add(instructor);
    }

}